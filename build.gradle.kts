plugins {
    id("java")
    id("org.sonarqube") version "4.4.1.3373"
}

group = "io.metis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sonar {
    properties {
        property("sonar.projectKey", "PatrickHaas_metis")
        property("sonar.organization", "patrick-haas")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "$rootDir/build/reports/jacoco/jacocoFullReport/jacocoFullReport.xml")
    }
}

subprojects {
    sonarqube {
        properties {
            property("sonar.coverage.jacoco.xmlReportPaths", "$projectDir.parentFile.path/build/reports/jacoco/codeCoverageReport/codeCoverageReport.xml")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}