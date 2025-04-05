package sorivma.geogeodataservice.integration.postgis

import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@Suppress("DEPRECATION")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class AbstractPostgisIntegrationTest {
    companion object {
        private val image: DockerImageName = DockerImageName.parse("postgis/postgis:16-3.4")
            .asCompatibleSubstituteFor("postgres")

        val container = PostgreSQLContainer(image).apply {
            withDatabaseName("test_db")
            withUsername("postgis")
            withPassword("postgis")
        }


    }

    @BeforeAll
    fun setupDatabase() {
        container.start()

        Database.connect(
            url = container.jdbcUrl,
            driver = container.driverClassName,
            user = container.username,
            password = container.password
        )

        val liquibase = Liquibase(
            "db/changelog/db.changelog-master.yaml",
            ClassLoaderResourceAccessor(),
            JdbcConnection(container.createConnection(""))
        )
        liquibase.update(Contexts(), LabelExpression())
    }

    @AfterAll
    fun tearDownDatabase() {
        container.close()
    }

    @BeforeEach
    fun beforeEach() {
        transaction {
            exec("TRUNCATE layers, features, feature_versions CASCADE;")
        }
    }
}