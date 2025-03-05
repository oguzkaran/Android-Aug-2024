
plugins {
    scala
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala3-library_3:3.0.1")
    testImplementation("org.scalatest:scalatest_3:3.2.9")
    testImplementation("junit:junit:4.13")
}

dependencies {
    implementation("commons-collections:commons-collections:3.2.2")
}
