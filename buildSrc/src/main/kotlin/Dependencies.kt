object Versions {
    const val kotlin = "1.9.25"
    const val springBoot = "3.2.4"
    const val dependencyManagement = "1.1.3"
    const val exposed = "0.60.0"
    const val postgres = "42.7.2"
    const val postgis = "2.3.0"
    const val testContainers = "1.19.7"
    const val junit = "5.10.2"
    const val junitRunner = "1.10.2"
}

object Deps {
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val postgres = "org.postgresql:postgresql:${Versions.postgres}"

    object Spring {
        const val boot = "org.springframework.boot:spring-boot-starter"
        const val web = "org.springframework.boot:spring-boot-starter-web"
        const val actuator = "org.springframework.boot:spring-boot-starter-actuator"
        const val jdbc = "org.springframework.boot:spring-boot-starter-jdbc"
        const val test = "org.springframework.boot:spring-boot-starter-test"
    }

    object Exposed {
        const val core = "org.jetbrains.exposed:exposed-core:${Versions.exposed}"
        const val dao = "org.jetbrains.exposed:exposed-dao:${Versions.exposed}"
        const val jdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}"
        const val time = "org.jetbrains.exposed:exposed-java-time:${Versions.exposed}"
    }

    object Postgis {
        const val jdbc = "net.postgis:postgis-jdbc:${Versions.postgis}"
    }

    object Liquibase {
        const val core = "org.liquibase:liquibase-core"
    }

    object Test {
        const val junitApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
        const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
        const val junitLauncher = "org.junit.platform:junit-platform-launcher:${Versions.junitRunner}"
        const val junitPlatformRunner = "org.junit.platform:junit-platform-runner:${Versions.junitRunner}"

        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit5:${Versions.kotlin}"

        const val testContainers = "org.testcontainers:testcontainers:${Versions.testContainers}"
        const val testContainersPostgres = "org.testcontainers:postgresql:${Versions.testContainers}"
    }
}