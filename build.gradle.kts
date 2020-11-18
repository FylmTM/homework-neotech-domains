import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
    kotlin("kapt") version "1.4.10"
}

group = "me.vrublevsky.neotech"
version = "1.0.0-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring Web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Spring Data
    // Nothing yet.

    // Spring Utilities
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    // HTTP
    implementation("io.github.openfeign:feign-okhttp:11.0")
    implementation("io.github.openfeign:feign-jackson:11.0")
    implementation("io.github.openfeign:feign-slf4j:11.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")

    // Utilities
    implementation("io.github.microutils:kotlin-logging:2.0.3")

    // Development
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.13.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_15
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
        languageVersion = "1.4"
    }
}
