/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.classpath.transforms;

import org.codehaus.groovy.runtime.ProcessGroovyMethods;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.vmplugin.v8.IndyInterface;
import org.gradle.api.file.RelativePath;
import org.gradle.internal.Pair;
import org.gradle.internal.classpath.CallInterceptionClosureInstrumentingClassVisitor;
import org.gradle.internal.classpath.ClassData;
import org.gradle.internal.classpath.ClasspathEntryVisitor;
import org.gradle.internal.classpath.Instrumented;
import org.gradle.internal.classpath.intercept.CallInterceptorRegistry;
import org.gradle.internal.classpath.intercept.JvmBytecodeInterceptorSet;
import org.gradle.internal.hash.Hasher;
import org.gradle.internal.instrumentation.api.jvmbytecode.JvmBytecodeCallInterceptor;
import org.gradle.internal.instrumentation.api.types.BytecodeInterceptorFilter;
import org.gradle.internal.lazy.Lazy;
import org.gradle.model.internal.asm.MethodVisitorScope;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static org.gradle.internal.classanalysis.AsmConstants.ASM_LEVEL;
import static org.gradle.internal.classpath.transforms.CommonTypes.NO_EXCEPTIONS;
import static org.gradle.internal.classpath.transforms.CommonTypes.STRING_TYPE;
import static org.gradle.internal.instrumentation.api.types.BytecodeInterceptorFilter.INSTRUMENTATION_ONLY;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Type.INT_TYPE;
import static org.objectweb.asm.Type.getMethodDescriptor;
import static org.objectweb.asm.Type.getObjectType;
import static org.objectweb.asm.Type.getType;

public class InstrumentingClassTransform implements ClassTransform {

    /**
     * Decoration format. Increment this when making changes.
     */
    private static final int DECORATION_FORMAT = 36;

    private static final Type SYSTEM_TYPE = getType(System.class);
    private static final Type INTEGER_TYPE = getType(Integer.class);
    private static final Type INSTRUMENTED_TYPE = getType(Instrumented.class);
    private static final Type LONG_TYPE = getType(Long.class);
    private static final Type BOOLEAN_TYPE = getType(Boolean.class);
    public static final Type PROPERTIES_TYPE = getType(Properties.class);
    private static final Type BYTECODE_INTERCEPTOR_FILTER_TYPE = Type.getType(BytecodeInterceptorFilter.class);

    private static final String RETURN_STRING_FROM_STRING = getMethodDescriptor(STRING_TYPE, STRING_TYPE);
    private static final String RETURN_STRING_FROM_STRING_STRING = getMethodDescriptor(STRING_TYPE, STRING_TYPE, STRING_TYPE);
    private static final String RETURN_STRING_FROM_STRING_STRING_STRING = getMethodDescriptor(STRING_TYPE, STRING_TYPE, STRING_TYPE, STRING_TYPE);
    private static final String RETURN_INTEGER_FROM_STRING = getMethodDescriptor(INTEGER_TYPE, STRING_TYPE);
    private static final String RETURN_INTEGER_FROM_STRING_INT = getMethodDescriptor(INTEGER_TYPE, STRING_TYPE, Type.INT_TYPE);
    private static final String RETURN_INTEGER_FROM_STRING_INTEGER = getMethodDescriptor(INTEGER_TYPE, STRING_TYPE, INTEGER_TYPE);
    private static final String RETURN_INTEGER_FROM_STRING_STRING = getMethodDescriptor(INTEGER_TYPE, STRING_TYPE, STRING_TYPE);
    private static final String RETURN_INTEGER_FROM_STRING_INT_STRING = getMethodDescriptor(INTEGER_TYPE, STRING_TYPE, Type.INT_TYPE, STRING_TYPE);
    private static final String RETURN_INTEGER_FROM_STRING_INTEGER_STRING = getMethodDescriptor(INTEGER_TYPE, STRING_TYPE, INTEGER_TYPE, STRING_TYPE);
    private static final String RETURN_LONG_FROM_STRING = getMethodDescriptor(LONG_TYPE, STRING_TYPE);
    private static final String RETURN_LONG_FROM_STRING_PRIMITIVE_LONG = getMethodDescriptor(LONG_TYPE, STRING_TYPE, Type.LONG_TYPE);
    private static final String RETURN_LONG_FROM_STRING_LONG = getMethodDescriptor(LONG_TYPE, STRING_TYPE, LONG_TYPE);
    private static final String RETURN_LONG_FROM_STRING_STRING = getMethodDescriptor(LONG_TYPE, STRING_TYPE, STRING_TYPE);
    private static final String RETURN_LONG_FROM_STRING_PRIMITIVE_LONG_STRING = getMethodDescriptor(LONG_TYPE, STRING_TYPE, Type.LONG_TYPE, STRING_TYPE);
    private static final String RETURN_LONG_FROM_STRING_LONG_STRING = getMethodDescriptor(LONG_TYPE, STRING_TYPE, LONG_TYPE, STRING_TYPE);
    private static final String RETURN_PRIMITIVE_BOOLEAN_FROM_STRING = getMethodDescriptor(Type.BOOLEAN_TYPE, STRING_TYPE);
    private static final String RETURN_PRIMITIVE_BOOLEAN_FROM_STRING_STRING = getMethodDescriptor(Type.BOOLEAN_TYPE, STRING_TYPE, STRING_TYPE);
    private static final String RETURN_PROPERTIES = getMethodDescriptor(PROPERTIES_TYPE);
    private static final String RETURN_PROPERTIES_FROM_STRING = getMethodDescriptor(PROPERTIES_TYPE, STRING_TYPE);
    private static final String RETURN_VOID_FROM_PROPERTIES = getMethodDescriptor(Type.VOID_TYPE, PROPERTIES_TYPE);
    private static final String RETURN_VOID_FROM_PROPERTIES_STRING = getMethodDescriptor(Type.VOID_TYPE, PROPERTIES_TYPE, STRING_TYPE);
    private static final String RETURN_CALL_SITE_ARRAY = getMethodDescriptor(getType(CallSiteArray.class));
    private static final String RETURN_VOID_FROM_CALL_SITE_ARRAY_BYTECODE_INTERCEPTOR = getMethodDescriptor(Type.VOID_TYPE, getType(CallSiteArray.class), BYTECODE_INTERCEPTOR_FILTER_TYPE);
    private static final String RETURN_MAP = getMethodDescriptor(getType(Map.class));
    private static final String RETURN_MAP_FROM_STRING = getMethodDescriptor(getType(Map.class), STRING_TYPE);

    private static final Type PROCESS_TYPE = getType(Process.class);
    private static final Type PROCESS_BUILDER_TYPE = getType(ProcessBuilder.class);
    private static final Type RUNTIME_TYPE = getType(Runtime.class);
    private static final Type PROCESS_GROOVY_METHODS_TYPE = getType(ProcessGroovyMethods.class);
    private static final Type STRING_ARRAY_TYPE = getType(String[].class);
    private static final Type FILE_TYPE = getType(File.class);
    private static final Type LIST_TYPE = getType(List.class);

    // ProcessBuilder().start() -> start(ProcessBuilder, String)
    private static final String RETURN_PROCESS = getMethodDescriptor(PROCESS_TYPE);
    private static final String RETURN_PROCESS_FROM_PROCESS_BUILDER_STRING = getMethodDescriptor(PROCESS_TYPE, PROCESS_BUILDER_TYPE, STRING_TYPE);
    // ProcessBuilder.startPipeline(List) -> startPipeline(List, String)
    private static final String RETURN_LIST_FROM_LIST = getMethodDescriptor(LIST_TYPE, LIST_TYPE);
    private static final String RETURN_LIST_FROM_LIST_STRING = getMethodDescriptor(LIST_TYPE, LIST_TYPE, STRING_TYPE);

    // Runtime().exec(String) -> exec(Runtime, String, String)
    // ProcessGroovyMethods.execute(String) -> execute(String, String)
    private static final String RETURN_PROCESS_FROM_STRING = getMethodDescriptor(PROCESS_TYPE, STRING_TYPE);
    private static final String RETURN_PROCESS_FROM_RUNTIME_STRING_STRING = getMethodDescriptor(PROCESS_TYPE, RUNTIME_TYPE, STRING_TYPE, STRING_TYPE);
    private static final String RETURN_PROCESS_FROM_STRING_STRING = getMethodDescriptor(PROCESS_TYPE, STRING_TYPE, STRING_TYPE);
    // Runtime().exec(String[]) -> exec(Runtime, String[], String)
    // ProcessGroovyMethods.execute(String[]) -> execute(String[], String)
    private static final String RETURN_PROCESS_FROM_STRING_ARRAY = getMethodDescriptor(PROCESS_TYPE, STRING_ARRAY_TYPE);
    private static final String RETURN_PROCESS_FROM_RUNTIME_STRING_ARRAY_STRING = getMethodDescriptor(PROCESS_TYPE, RUNTIME_TYPE, STRING_ARRAY_TYPE, STRING_TYPE);
    private static final String RETURN_PROCESS_FROM_STRING_ARRAY_STRING = getMethodDescriptor(PROCESS_TYPE, STRING_ARRAY_TYPE, STRING_TYPE);
    // ProcessGroovyMethods.execute(List) -> execute(List, String)
    private static final String RETURN_PROCESS_FROM_LIST = getMethodDescriptor(PROCESS_TYPE, LIST_TYPE);
    private static final String RETURN_PROCESS_FROM_LIST_STRING = getMethodDescriptor(PROCESS_TYPE, LIST_TYPE, STRING_TYPE);
    // Runtime().exec(String, String[]) -> exec(Runtume, String, String[], String)
    private static final String RETURN_PROCESS_FROM_STRING_STRING_ARRAY = getMethodDescriptor(PROCESS_TYPE, STRING_TYPE, STRING_ARRAY_TYPE);
    private static final String RETURN_PROCESS_FROM_RUNTIME_STRING_STRING_ARRAY_STRING = getMethodDescriptor(PROCESS_TYPE, RUNTIME_TYPE, STRING_TYPE, STRING_ARRAY_TYPE, STRING_TYPE);
    // Runtime().exec(String[], String[]) -> exec(Runtume, String[], String[], String)
    private static final String RETURN_PROCESS_FROM_STRING_ARRAY_STRING_ARRAY = getMethodDescriptor(PROCESS_TYPE, STRING_ARRAY_TYPE, STRING_ARRAY_TYPE);
    private static final String RETURN_PROCESS_FROM_RUNTIME_STRING_ARRAY_STRING_ARRAY_STRING = getMethodDescriptor(PROCESS_TYPE, RUNTIME_TYPE, STRING_ARRAY_TYPE, STRING_ARRAY_TYPE, STRING_TYPE);
    // Runtime().exec(String, String[], File) -> exec(Runtime, String, String[], File, String)
    // ProcessGroovyMethods.execute(String, String[], File) -> execute(String, String[], File, String)
    private static final String RETURN_PROCESS_FROM_STRING_STRING_ARRAY_FILE = getMethodDescriptor(PROCESS_TYPE, STRING_TYPE, STRING_ARRAY_TYPE, FILE_TYPE);
    private static final String RETURN_PROCESS_FROM_RUNTIME_STRING_STRING_ARRAY_FILE_STRING = getMethodDescriptor(PROCESS_TYPE, RUNTIME_TYPE, STRING_TYPE, STRING_ARRAY_TYPE, FILE_TYPE, STRING_TYPE);
    private static final String RETURN_PROCESS_FROM_STRING_STRING_ARRAY_FILE_STRING = getMethodDescriptor(PROCESS_TYPE, STRING_TYPE, STRING_ARRAY_TYPE, FILE_TYPE, STRING_TYPE);
    // Runtime().exec(String[], String[], File) -> exec(Runtime, String[], String[], File, String)
    // ProcessGroovyMethods.execute(String[], String[], File) -> execute(String[], String[], File, String)
    private static final String RETURN_PROCESS_FROM_STRING_ARRAY_STRING_ARRAY_FILE = getMethodDescriptor(PROCESS_TYPE, STRING_ARRAY_TYPE, STRING_ARRAY_TYPE, FILE_TYPE);
    private static final String RETURN_PROCESS_FROM_RUNTIME_STRING_ARRAY_STRING_ARRAY_FILE_STRING = getMethodDescriptor(PROCESS_TYPE, RUNTIME_TYPE, STRING_ARRAY_TYPE, STRING_ARRAY_TYPE, FILE_TYPE, STRING_TYPE);
    private static final String RETURN_PROCESS_FROM_STRING_ARRAY_STRING_ARRAY_FILE_STRING = getMethodDescriptor(PROCESS_TYPE, STRING_ARRAY_TYPE, STRING_ARRAY_TYPE, FILE_TYPE, STRING_TYPE);
    // ProcessGroovyMethods.execute(List, String[], File) -> execute(List, String[], File, String)
    private static final String RETURN_PROCESS_FROM_LIST_STRING_ARRAY_FILE = getMethodDescriptor(PROCESS_TYPE, LIST_TYPE, STRING_ARRAY_TYPE, FILE_TYPE);
    private static final String RETURN_PROCESS_FROM_LIST_STRING_ARRAY_FILE_STRING = getMethodDescriptor(PROCESS_TYPE, LIST_TYPE, STRING_ARRAY_TYPE, FILE_TYPE, STRING_TYPE);
    // ProcessGroovyMethods.execute(String, List, File) -> execute(String, List, File, String)
    private static final String RETURN_PROCESS_FROM_STRING_LIST_FILE = getMethodDescriptor(PROCESS_TYPE, STRING_TYPE, LIST_TYPE, FILE_TYPE);
    private static final String RETURN_PROCESS_FROM_STRING_LIST_FILE_STRING = getMethodDescriptor(PROCESS_TYPE, STRING_TYPE, LIST_TYPE, FILE_TYPE, STRING_TYPE);
    // ProcessGroovyMethods.execute(String[], List, File) -> execute(String[], List, File, String)
    private static final String RETURN_PROCESS_FROM_STRING_ARRAY_LIST_FILE = getMethodDescriptor(PROCESS_TYPE, STRING_ARRAY_TYPE, LIST_TYPE, FILE_TYPE);
    private static final String RETURN_PROCESS_FROM_STRING_ARRAY_LIST_FILE_STRING = getMethodDescriptor(PROCESS_TYPE, STRING_ARRAY_TYPE, LIST_TYPE, FILE_TYPE, STRING_TYPE);
    // ProcessGroovyMethods.execute(List, List, File) -> execute(List, List, File, String)
    private static final String RETURN_PROCESS_FROM_LIST_LIST_FILE = getMethodDescriptor(PROCESS_TYPE, LIST_TYPE, LIST_TYPE, FILE_TYPE);
    private static final String RETURN_PROCESS_FROM_LIST_LIST_FILE_STRING = getMethodDescriptor(PROCESS_TYPE, LIST_TYPE, LIST_TYPE, FILE_TYPE, STRING_TYPE);

    private static final String GROOVY_INDY_INTERFACE_TYPE = getType(IndyInterface.class).getInternalName();

    @SuppressWarnings("deprecation")
    private static final String GROOVY_INDY_INTERFACE_V7_TYPE = getType(org.codehaus.groovy.vmplugin.v7.IndyInterface.class).getInternalName();
    private static final String GROOVY_INDY_INTERFACE_BOOTSTRAP_METHOD_DESCRIPTOR = getMethodDescriptor(getType(CallSite.class), getType(MethodHandles.Lookup.class), STRING_TYPE, getType(MethodType.class), STRING_TYPE, INT_TYPE);

    private static final String INSTRUMENTED_CALL_SITE_METHOD = "$instrumentedCallSiteArray";
    private static final String CREATE_CALL_SITE_ARRAY_METHOD = "$createCallSiteArray";

    private final JvmBytecodeInterceptorSet externalInterceptors;

    @Override
    public void applyConfigurationTo(Hasher hasher) {
        hasher.putString(InstrumentingClassTransform.class.getSimpleName());
        hasher.putInt(DECORATION_FORMAT);
    }

    public InstrumentingClassTransform() {
        this(INSTRUMENTATION_ONLY);
    }

    public InstrumentingClassTransform(BytecodeInterceptorFilter interceptorFilter) {
        this.externalInterceptors = CallInterceptorRegistry.getJvmBytecodeInterceptors(interceptorFilter);
    }

    @Override
    public Pair<RelativePath, ClassVisitor> apply(ClasspathEntryVisitor.Entry entry, ClassVisitor visitor, ClassData classData) {
        return Pair.of(entry.getPath(),
            new InstrumentingVisitor(
                new CallInterceptionClosureInstrumentingClassVisitor(
                    new LambdaSerializationTransformer(new InstrumentingBackwardsCompatibilityVisitor(visitor)),
                    externalInterceptors.getOriginalFilter()
                ),
                classData, externalInterceptors
            )
        );
    }

    private static class InstrumentingVisitor extends ClassVisitor {
        String className;
        private final ClassData classData;
        private boolean hasGroovyCallSites;
        private final JvmBytecodeInterceptorSet externalInterceptors;

        public InstrumentingVisitor(ClassVisitor visitor, ClassData classData, JvmBytecodeInterceptorSet externalInterceptors) {
            super(ASM_LEVEL, visitor);
            this.classData = classData;
            this.externalInterceptors = externalInterceptors;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            this.className = name;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals(CREATE_CALL_SITE_ARRAY_METHOD) && descriptor.equals(RETURN_CALL_SITE_ARRAY)) {
                hasGroovyCallSites = true;
            }
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            Lazy<MethodNode> asMethodNode = Lazy.unsafe().of(() -> {
                Optional<MethodNode> methodNode = classData.readClassAsNode().methods.stream().filter(method ->
                        Objects.equals(method.name, name) && Objects.equals(method.desc, descriptor) && Objects.equals(method.signature, signature)
                ).findFirst();
                return methodNode.orElseThrow(() -> new IllegalStateException("could not find method " + name + " with descriptor " + descriptor));
            });
            return new InstrumentingMethodVisitor(this, methodVisitor, asMethodNode, classData, externalInterceptors);
        }

        @Override
        public void visitEnd() {
            if (hasGroovyCallSites) {
                generateCallSiteFactoryMethod();
            }
            super.visitEnd();
        }

        private void generateCallSiteFactoryMethod() {
            new MethodVisitorScope(visitStaticPrivateMethod(INSTRUMENTED_CALL_SITE_METHOD, RETURN_CALL_SITE_ARRAY)) {{
                _INVOKESTATIC(className, CREATE_CALL_SITE_ARRAY_METHOD, RETURN_CALL_SITE_ARRAY);
                _DUP();
                _GETSTATIC(BYTECODE_INTERCEPTOR_FILTER_TYPE, externalInterceptors.getOriginalFilter().name(), BYTECODE_INTERCEPTOR_FILTER_TYPE.getDescriptor());
                _INVOKESTATIC(INSTRUMENTED_TYPE, "groovyCallSites", RETURN_VOID_FROM_CALL_SITE_ARRAY_BYTECODE_INTERCEPTOR);
                _ARETURN();
                visitMaxs(2, 0);
                visitEnd();
            }};
        }

        private MethodVisitor visitStaticPrivateMethod(String name, String descriptor) {
            return super.visitMethod(ACC_STATIC | ACC_SYNTHETIC | ACC_PRIVATE, name, descriptor, null, NO_EXCEPTIONS);
        }
    }

    private static class InstrumentingMethodVisitor extends MethodVisitorScope {
        private final InstrumentingVisitor owner;
        private final String className;
        private final Lazy<MethodNode> asNode;
        private final Collection<JvmBytecodeCallInterceptor> externalInterceptors;
        private final BytecodeInterceptorFilter interceptorFilter;

        public InstrumentingMethodVisitor(InstrumentingVisitor owner, MethodVisitor methodVisitor, Lazy<MethodNode> asNode, ClassData classData, JvmBytecodeInterceptorSet externalInterceptors) {
            super(methodVisitor);
            this.owner = owner;
            this.className = owner.className;
            this.asNode = asNode;
            this.interceptorFilter = externalInterceptors.getOriginalFilter();
            this.externalInterceptors = externalInterceptors.getInterceptors(methodVisitor, classData);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (opcode == INVOKESTATIC && visitINVOKESTATIC(owner, name, descriptor)) {
                return;
            }
            if (opcode == INVOKEVIRTUAL && visitINVOKEVIRTUAL(owner, name, descriptor)) {
                return;
            }
            if (opcode == INVOKESPECIAL && visitINVOKESPECIAL(owner, name, descriptor)) {
                return;
            }

            for (JvmBytecodeCallInterceptor generatedInterceptor : externalInterceptors) {
                if (generatedInterceptor.visitMethodInsn(className, opcode, owner, name, descriptor, isInterface, asNode)) {
                    return;
                }
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        private boolean visitINVOKESTATIC(String owner, String name, String descriptor) {
            // TODO - load the class literal instead of class name to pass to the methods on Instrumented
            if (owner.equals(SYSTEM_TYPE.getInternalName())) {
                if (name.equals("getProperty")) {
                    if (descriptor.equals(RETURN_STRING_FROM_STRING)) {
                        _LDC(binaryClassNameOf(className));
                        _INVOKESTATIC(INSTRUMENTED_TYPE, "systemProperty", RETURN_STRING_FROM_STRING_STRING);
                        return true;
                    }
                    if (descriptor.equals(RETURN_STRING_FROM_STRING_STRING)) {
                        _LDC(binaryClassNameOf(className));
                        _INVOKESTATIC(INSTRUMENTED_TYPE, "systemProperty", RETURN_STRING_FROM_STRING_STRING_STRING);
                        return true;
                    }
                } else if (name.equals("getProperties") && descriptor.equals(RETURN_PROPERTIES)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "systemProperties", RETURN_PROPERTIES_FROM_STRING);
                    return true;
                } else if (name.equals("setProperties") && descriptor.equals(RETURN_VOID_FROM_PROPERTIES)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "setSystemProperties", RETURN_VOID_FROM_PROPERTIES_STRING);
                    return true;
                } else if (name.equals("setProperty") && descriptor.equals(RETURN_STRING_FROM_STRING_STRING)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "setSystemProperty", RETURN_STRING_FROM_STRING_STRING_STRING);
                    return true;
                } else if (name.equals("clearProperty") && descriptor.equals(RETURN_STRING_FROM_STRING)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "clearSystemProperty", RETURN_STRING_FROM_STRING_STRING);
                    return true;
                } else if (name.equals("getenv")) {
                    if (descriptor.equals(RETURN_STRING_FROM_STRING)) {
                        // System.getenv(String) -> String
                        _LDC(binaryClassNameOf(className));
                        _INVOKESTATIC(INSTRUMENTED_TYPE, "getenv", RETURN_STRING_FROM_STRING_STRING);
                        return true;
                    } else if (descriptor.equals(RETURN_MAP)) {
                        // System.getenv() -> Map<String, String>
                        _LDC(binaryClassNameOf(className));
                        _INVOKESTATIC(INSTRUMENTED_TYPE, "getenv", RETURN_MAP_FROM_STRING);
                        return true;
                    }
                }
            } else if (owner.equals(INTEGER_TYPE.getInternalName()) && name.equals("getInteger")) {
                if (descriptor.equals(RETURN_INTEGER_FROM_STRING)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "getInteger", RETURN_INTEGER_FROM_STRING_STRING);
                    return true;
                }
                if (descriptor.equals(RETURN_INTEGER_FROM_STRING_INT)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "getInteger", RETURN_INTEGER_FROM_STRING_INT_STRING);
                    return true;
                }
                if (descriptor.equals(RETURN_INTEGER_FROM_STRING_INTEGER)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "getInteger", RETURN_INTEGER_FROM_STRING_INTEGER_STRING);
                    return true;
                }
            } else if (owner.equals(LONG_TYPE.getInternalName()) && name.equals("getLong")) {
                if (descriptor.equals(RETURN_LONG_FROM_STRING)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "getLong", RETURN_LONG_FROM_STRING_STRING);
                    return true;
                }
                if (descriptor.equals(RETURN_LONG_FROM_STRING_PRIMITIVE_LONG)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "getLong", RETURN_LONG_FROM_STRING_PRIMITIVE_LONG_STRING);
                    return true;
                }
                if (descriptor.equals(RETURN_LONG_FROM_STRING_LONG)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "getLong", RETURN_LONG_FROM_STRING_LONG_STRING);
                    return true;
                }
            } else if (owner.equals(BOOLEAN_TYPE.getInternalName()) && name.equals("getBoolean") && descriptor.equals(RETURN_PRIMITIVE_BOOLEAN_FROM_STRING)) {
                _LDC(binaryClassNameOf(className));
                _INVOKESTATIC(INSTRUMENTED_TYPE, "getBoolean", RETURN_PRIMITIVE_BOOLEAN_FROM_STRING_STRING);
                return true;
            } else if (owner.equals(PROCESS_GROOVY_METHODS_TYPE.getInternalName()) && name.equals("execute")) {
                Optional<String> instrumentedDescriptor = getInstrumentedDescriptorForProcessGroovyMethodsExecuteDescriptor(descriptor);
                if (!instrumentedDescriptor.isPresent()) {
                    return false;
                }
                _LDC(binaryClassNameOf(className));
                _INVOKESTATIC(INSTRUMENTED_TYPE, "execute", instrumentedDescriptor.get());
                return true;
            }
            if (owner.equals(PROCESS_BUILDER_TYPE.getInternalName()) && name.equals("startPipeline") && descriptor.equals(RETURN_LIST_FROM_LIST)) {
                _LDC(binaryClassNameOf(className));
                _INVOKESTATIC(INSTRUMENTED_TYPE, "startPipeline", RETURN_LIST_FROM_LIST_STRING);
                return true;
            } else if (owner.equals(className) && name.equals(CREATE_CALL_SITE_ARRAY_METHOD) && descriptor.equals(RETURN_CALL_SITE_ARRAY)) {
                _INVOKESTATIC(className, INSTRUMENTED_CALL_SITE_METHOD, RETURN_CALL_SITE_ARRAY);
                return true;
            }
            return false;
        }

        private boolean visitINVOKEVIRTUAL(String owner, String name, String descriptor) {
            // Runtime.exec(...)
            if (owner.equals(RUNTIME_TYPE.getInternalName()) && name.equals("exec")) {
                Optional<String> instrumentedDescriptor = getInstrumentedDescriptorForRuntimeExecDescriptor(descriptor);
                if (!instrumentedDescriptor.isPresent()) {
                    return false;
                }
                _LDC(binaryClassNameOf(className));
                _INVOKESTATIC(INSTRUMENTED_TYPE, "exec", instrumentedDescriptor.get());
                return true;
            }
            if (owner.equals(PROCESS_BUILDER_TYPE.getInternalName())) {
                if (name.equals("start") && descriptor.equals(RETURN_PROCESS)) {
                    _LDC(binaryClassNameOf(className));
                    _INVOKESTATIC(INSTRUMENTED_TYPE, "start", RETURN_PROCESS_FROM_PROCESS_BUILDER_STRING);
                    return true;
                }
            }
            return false;
        }

        private Optional<String> getInstrumentedDescriptorForProcessGroovyMethodsExecuteDescriptor(String descriptor) {
            if (descriptor.equals(RETURN_PROCESS_FROM_STRING)) {
                // execute(String)
                return Optional.of(RETURN_PROCESS_FROM_STRING_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_ARRAY)) {
                // execute(String[])
                return Optional.of(RETURN_PROCESS_FROM_STRING_ARRAY_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_LIST)) {
                // execute(List)
                return Optional.of(RETURN_PROCESS_FROM_LIST_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_STRING_ARRAY_FILE)) {
                // execute(String, String[], File)
                return Optional.of(RETURN_PROCESS_FROM_STRING_STRING_ARRAY_FILE_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_ARRAY_STRING_ARRAY_FILE)) {
                // execute(String[], String[], File)
                return Optional.of(RETURN_PROCESS_FROM_STRING_ARRAY_STRING_ARRAY_FILE_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_LIST_STRING_ARRAY_FILE)) {
                // execute(List, String[], File)
                return Optional.of(RETURN_PROCESS_FROM_LIST_STRING_ARRAY_FILE_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_LIST_FILE)) {
                // execute(String, List, File)
                return Optional.of(RETURN_PROCESS_FROM_STRING_LIST_FILE_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_ARRAY_LIST_FILE)) {
                // execute(String[], List, File)
                return Optional.of(RETURN_PROCESS_FROM_STRING_ARRAY_LIST_FILE_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_LIST_LIST_FILE)) {
                // execute(List, List, File)
                return Optional.of(RETURN_PROCESS_FROM_LIST_LIST_FILE_STRING);
            }
            // It is some signature of ProcessGroovyMethods.execute that we don't know about.
            return Optional.empty();
        }

        private Optional<String> getInstrumentedDescriptorForRuntimeExecDescriptor(String descriptor) {
            if (descriptor.equals(RETURN_PROCESS_FROM_STRING)) {
                return Optional.of(RETURN_PROCESS_FROM_RUNTIME_STRING_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_ARRAY)) {
                return Optional.of(RETURN_PROCESS_FROM_RUNTIME_STRING_ARRAY_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_STRING_ARRAY)) {
                return Optional.of(RETURN_PROCESS_FROM_RUNTIME_STRING_STRING_ARRAY_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_ARRAY_STRING_ARRAY)) {
                return Optional.of(RETURN_PROCESS_FROM_RUNTIME_STRING_ARRAY_STRING_ARRAY_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_STRING_ARRAY_FILE)) {
                return Optional.of(RETURN_PROCESS_FROM_RUNTIME_STRING_STRING_ARRAY_FILE_STRING);
            } else if (descriptor.equals(RETURN_PROCESS_FROM_STRING_ARRAY_STRING_ARRAY_FILE)) {
                return Optional.of(RETURN_PROCESS_FROM_RUNTIME_STRING_ARRAY_STRING_ARRAY_FILE_STRING);
            }
            // It is some signature of Runtime.exec that we don't know about.
            return Optional.empty();
        }

        private boolean visitINVOKESPECIAL(String owner, String name, String descriptor) {
            return false;
        }

        @Override
        public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
            if (isGroovyIndyCallsite(bootstrapMethodHandle)) {
                Handle interceptor = new Handle(
                    H_INVOKESTATIC,
                    INSTRUMENTED_TYPE.getInternalName(),
                    getBoostrapMethodName(interceptorFilter),
                    GROOVY_INDY_INTERFACE_BOOTSTRAP_METHOD_DESCRIPTOR,
                    false
                );
                super.visitInvokeDynamicInsn(name, descriptor, interceptor, bootstrapMethodArguments);
            } else {
                super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
            }
        }

        private static String getBoostrapMethodName(BytecodeInterceptorFilter interceptorFilter) {
            switch (interceptorFilter) {
                case INSTRUMENTATION_ONLY:
                    return "bootstrapInstrumentationOnly";
                case ALL:
                    return "bootstrapAll";
                default:
                    throw new UnsupportedOperationException("Unknown interceptor request: " + interceptorFilter);
            }
        }

        private String binaryClassNameOf(String className) {
            return getObjectType(className).getClassName();
        }

        private boolean isGroovyIndyCallsite(Handle bootstrapMethodHandle) {
            return (bootstrapMethodHandle.getOwner().equals(GROOVY_INDY_INTERFACE_TYPE) ||
                bootstrapMethodHandle.getOwner().equals(GROOVY_INDY_INTERFACE_V7_TYPE)) &&
                bootstrapMethodHandle.getName().equals("bootstrap") &&
                bootstrapMethodHandle.getDesc().equals(GROOVY_INDY_INTERFACE_BOOTSTRAP_METHOD_DESCRIPTOR);
        }
    }
}
