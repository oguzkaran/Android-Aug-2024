plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.csystem"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.csystem.app.AppKt"
    }
}

dependencies {
    implementation("org.csystem:org-csystem-kotlin-util-console:1.0.0")
    implementation("org.csystem:org-csystem-kotlin-math:1.0.0")
    implementation("org.csystem:org-csystem-kotlin-util:1.0.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}