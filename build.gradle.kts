plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.spring") version Versions.kotlin
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version Versions.dependencyManagement
}

group = "sorivma"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Deps.Spring.boot)
    implementation(Deps.Spring.web)
    implementation(Deps.Spring.jdbc)
    implementation(Deps.Spring.actuator)

    implementation(Deps.kotlinReflect)
    implementation(Deps.postgres)

    implementation(Deps.Exposed.core)
    implementation(Deps.Exposed.dao)
    implementation(Deps.Exposed.jdbc)
    implementation(Deps.Exposed.time)

    implementation(Deps.Liquibase.core)

    testImplementation(Deps.Spring.test)
    testImplementation(Deps.Test.kotlinTest)
    testRuntimeOnly(Deps.Test.junitLauncher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}