package io.github.emikaelsilveira.tests.components

import com.opentable.db.postgres.embedded.EmbeddedPostgres
import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.resources.extensions.dtoToSchema
import io.github.emikaelsilveira.resources.repositories.schemas.AddressSchema
import io.github.emikaelsilveira.resources.repositories.schemas.UserSchema
import io.github.emikaelsilveira.tests.builders.AddressDTOBuilder
import io.github.emikaelsilveira.tests.builders.UserDTOBuilder
import org.flywaydb.core.Flyway
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

    private val flywayConfiguration = Flyway.configure().run {
        dataSource(
            JDBC_URL,
            DB_USER_NAME,
            DB_PASSWORD
        ).schemas(DB_SCHEMA).load()
    }

    fun init() {
        embeddedPostgres = EmbeddedPostgres.builder().apply {
            setPort(MOCK_DB_PORT)
        }.start()
    }

    fun close() {
        embeddedPostgres.close()
    }

    fun clearDatabase(): Flyway = flywayConfiguration.apply {
        clean()
        migrate()
    }

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
