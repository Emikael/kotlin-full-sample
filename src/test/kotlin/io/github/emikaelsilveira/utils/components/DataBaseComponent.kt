package io.github.emikaelsilveira.utils.components

import com.opentable.db.postgres.embedded.EmbeddedPostgres
import io.github.emikaelsilveira.application.config.database.DatabaseConfig
import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.resources.extensions.dtoToSchema
import io.github.emikaelsilveira.resources.repositories.schemas.AddressSchema
import io.github.emikaelsilveira.resources.repositories.schemas.UserSchema
import io.github.emikaelsilveira.utils.builders.AddressDTOBuilder
import io.github.emikaelsilveira.utils.builders.UserDTOBuilder
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.LocalDateTime

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

    fun insertUser(user: UserDTO = UserDTOBuilder.build()) {
        transaction {
            UserSchema.insert {
                dtoToSchema(it, user)
                it[createdAt] = LocalDateTime.now().toDateTime()
            }
        }
    }

    fun insertAddress(address: AddressDTO = AddressDTOBuilder.build()) {
        transaction {
            AddressSchema.insert {
                dtoToSchema(it, address)
                it[createdAt] = LocalDateTime.now().toDateTime()
            }
        }
    }
}
