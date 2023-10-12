rootProject.name = "metis"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("lombok", "org.projectlombok:lombok:1.18.28")
            library("slf4j-api", "org.slf4j:slf4j-api:2.0.7")
            library("logback-core", "ch.qos.logback:logback-core:1.4.11")

            library("jmolecules-ddd", "org.jmolecules:jmolecules-ddd:1.7.0")
            library("jmolecules-events", "org.jmolecules:jmolecules-events:1.7.0")
            library("jmolecules-hexagonal-architecture", "org.jmolecules:jmolecules-hexagonal-architecture:1.7.0")

            library("junit-jupiter-bom", "org.junit:junit-bom:5.9.1")

            library("mockito-core", "org.mockito:mockito-core:5.5.0")
            library("mockito-junit-jupiter", "org.mockito:mockito-junit-jupiter:5.5.0")

            library("assertj-core", "org.assertj:assertj-core:3.24.2")
        }
    }
}

include("metis-common:metis-common-domain")
include("metis-common:metis-common-application")
include("metis-common:metis-common-adapters")

include("metis-employees:metis-employees-domain")
include("metis-employees:metis-employees-application")
include("metis-employees:metis-employees-adapters")