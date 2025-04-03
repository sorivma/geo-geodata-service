object Versions {
    const val kotlin = "1.9.25"
    const val springBoot = "3.2.4"
    const val dependencyManagement = "1.1.3"
    const val exposed = "0.60.0"
    const val postgres = "42.7.2"
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

    object Liquibase {
        const val core = "org.liquibase:liquibase-core"
    }

    object Test {
        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit5"
        const val junitLauncher = "org.junit.platform:junit-platform-launcher"
    }
}