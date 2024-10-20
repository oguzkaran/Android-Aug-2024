plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.csystem"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.csystem:org-csystem-kotlin-util-console:1.0.0")
    implementation("org.csystem:org-csystem-kotlin-math:1.0.0")
    implementation("org.csystem:org-csystem-kotlin-util:1.0.0")

    implementation("org.csystem:org-csystem-kotlin-data-processing-test:1.0.0")

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