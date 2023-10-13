plugins {
    id("java")
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    jacoco
    id("org.sonarqube") version "4.4.1.3373"
}

group = "io.metis.employees"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.logback.core)

    implementation(project(":metis-common:metis-common-domain"))
    implementation(project(":metis-common:metis-common-application"))
    implementation(project(":metis-common:metis-common-adapters"))
    implementation(project(":metis-mitarbeiter:metis-mitarbeiter-domain"))

    implementation(project(":metis-mitarbeiter:metis-mitarbeiter-application"))
    implementation(project(":metis-mitarbeiter:metis-mitarbeiter-adapters"))

    implementation("org.reflections:reflections:0.10.2")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.6.0")

    implementation("org.keycloak:keycloak-admin-client:21.1.2")

    testImplementation(platform(libs.junit.jupiter.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")

    testImplementation("org.testcontainers:postgresql:1.18.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
    }
}