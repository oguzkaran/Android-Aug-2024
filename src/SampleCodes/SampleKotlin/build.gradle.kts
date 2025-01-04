plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.csystem"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://raw.github.com/oguzkaran/android-aug-2024-maven-repo/main")
    }
    maven {
        url = uri("https://raw.github.com/oguzkaran/android-aug-2024-karandev-maven-repo/main")
    }
}

dependencies {
    implementation("org.csystem:org-csystem-kotlin-util-console:8.0.0")
    implementation("org.csystem:org-csystem-kotlin-math:8.0.0")
    implementation("org.csystem:org-csystem-kotlin-util:8.0.0")
    implementation("org.csystem:org-csystem-kotlin-util-io:8.0.0")

    implementation("org.csystem:org-csystem-kotlin-data-processing-test:8.0.0")
    implementation("com.karandev:com-karandev-data:8.0.0")

    testImplementation(kotlin("test"))
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.csystem.app.AppKt"
    }

    from(configurations.compileClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
    duplicatesStrategy  = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}