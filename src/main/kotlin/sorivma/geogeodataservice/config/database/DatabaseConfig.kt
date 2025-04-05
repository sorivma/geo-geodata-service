package sorivma.geogeodataservice.config.database

import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.postgis.PGgeometry
import org.postgresql.PGConnection
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import java.sql.Connection
import javax.sql.DataSource

@Configuration
class DatabaseConfig(
    private val datasource: DataSource,
) {

    private val logger = LoggerFactory.getLogger(DatabaseConfig::class.java)

    @PostConstruct
    fun init() {
        logger.info("Initializing PostgreSQL/Postgis Exposed connectivity")

        Database.connect(datasource)

        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ

        checkConnectivity()
        registerPostgisTypes()

        logger.info("PostgreSQL/Liquibase initialization complete")
    }

    private fun checkConnectivity() {
        datasource.connection.use { conn ->
            val product = conn.metaData.databaseProductName
            val version = conn.metaData.databaseProductVersion
            logger.info("Connected to database: $product v$version")
        }
    }

    private fun registerPostgisTypes() {
        datasource.connection.use { conn ->
            val pgConnection = conn.unwrap(PGConnection::class.java)
            pgConnection.addDataType("geometry", PGgeometry::class.java)
            logger.info("Registered Postgis types")
        }
    }
}