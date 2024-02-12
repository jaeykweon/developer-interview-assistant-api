import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val asciidoctorExt: Configuration by configurations.creating

plugins {
    val kotlinVersion = "1.9.22"

    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion

    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "org.idd"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    val kotestVersion = "5.5.5"
    val jdslVersion = "3.2.0"

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    runtimeOnly("com.h2database:h2:2.1.214")
//    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("org.postgresql:postgresql:42.6.0")

    implementation("org.springframework.security:spring-security-crypto:6.0.2")
    implementation("com.auth0:java-jwt:4.4.0")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")

    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:$jdslVersion")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:$jdslVersion")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:$jdslVersion")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-javax-support:$jdslVersion")

    testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

tasks {
    val snippetsDir = file("build/generated-snippets")

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "21"
        }
    }

    withType<Test> {
        outputs.dir(snippetsDir)
        useJUnitPlatform()
    }

    configurations {
        asciidoctorExt
    }

    asciidoctor {
        configurations(asciidoctorExt.name)
        inputs.dir(snippetsDir)
        dependsOn(test)
    }
}

ktlint {
    debug.set(true)
    charset("UTF-8")
}
