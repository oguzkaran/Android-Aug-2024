plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "org.csystem"
version = "1.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	mavenLocal()
	maven {
		url = uri("https://raw.github.com/oguzkaran/javaapp2-jan-2024-maven-repo/main")
	}

	maven {
		url = uri("https://raw.github.com/oguzkaran/javaapp2-jan-2024-karandev-maven-repo/main")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")

	compileOnly("org.projectlombok:lombok:1.18.38")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")


	implementation("org.openpnp:opencv:4.9.0-0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
