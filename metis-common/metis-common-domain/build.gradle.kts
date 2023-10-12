plugins {
    id("java")
}

group = "io.metis.common"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.jmolecules.ddd)
    implementation(libs.jmolecules.hexagonal.architecture)

    implementation(libs.slf4j.api)

    testImplementation(platform(libs.junit.jupiter.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)

    testImplementation(libs.assertj.core)
}

tasks.test {
    useJUnitPlatform()
}