package io.github.emikaelsilveira.components

import com.opentable.db.postgres.embedded.EmbeddedPostgres
import io.github.emikaelsilveira.application.config.database.DatabaseConfig
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

object DataBaseComponent {

    private const val JDBC_URL = "jdbc:postgresql://localhost:5432/kotlinfullsample"
    private const val DB_USER_NAME = "postgres"
    private const val DB_PASSWORD = "postgres"
    private const val DB_SCHEMA = "public"
    private const val MOCK_DB_PORT = 32971

    private lateinit var embeddedPostgres: EmbeddedPostgres
    private lateinit var dataBaseConfig: DatabaseConfig

    private val flywayConfiguration = Flyway.configure().run {
        dataSource(
            JDBC_URL,
            DB_USER_NAME,
            DB_PASSWORD
        ).schemas(DB_SCHEMA).load()
    }

    fun init() {
        dataBaseConfig = DatabaseConfig()
        embeddedPostgres = EmbeddedPostgres.builder().apply {
            setPort(MOCK_DB_PORT)
        }.start().also {
            flywayConfiguration.migrate()
            Database.connect(dataBaseConfig.dataSource)
        }
    }

    fun close() {
        with(getDataSource().connection) {
            if (!isClosed) close()
        }
        embeddedPostgres.close()
    }

    fun clearDatabase(): Flyway = flywayConfiguration.apply {
        clean()
        migrate()
    }

    fun getDataSource() = dataBaseConfig.dataSource
}
