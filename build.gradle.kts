plugins {
    java
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    id("org.openapi.generator") version "7.4.0"
}

group = "toni.aguilera"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$rootDir/src/main/resources/openapi.yml")
    outputDir.set("${project.buildDir}/generated")
    apiPackage.set("toni.aguilera.generated.api")
    modelPackage.set("toni.aguilera.generated.model")
    invokerPackage.set("toni.aguilera.generated")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "skipDefaultInterface" to "false",
            "useSpringBoot3" to "true",
            "java8" to "true",
            "dateLibrary" to "java8",
            "useTags" to "true",
            "documentationProvider" to "none",
            "openApiNullable" to "false",
            "useOptional" to "true"
        )
    )
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/src/main/java")
        }
    }
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("org.projectlombok:lombok")
    implementation("com.h2database:h2")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter:1.19.1")
    testImplementation("org.testcontainers:jdbc:1.19.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
}
