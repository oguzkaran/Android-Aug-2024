package org.csystem.app.demo.java;

class A extends B implements IX, IY {
    @Override
    public void foo()
    {
        System.out.println("A.foo");
        }
}
class B {
    public void bar()
    {
        System.out.println("B.bar");
    }

    public void tar()
    {
        System.out.println("B.tar");
    }
}

interface IY {
    void foo();
    default void bar()
    {
        System.out.println("IY.bar");
    }
}

interface IX {
    void foo();
    default void bar()
    {
        System.out.println("IX.bar");
    }
}