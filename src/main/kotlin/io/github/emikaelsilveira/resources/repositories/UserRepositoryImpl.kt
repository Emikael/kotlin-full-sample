package io.github.emikaelsilveira.resources.repositories

import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.domain.exceptions.NotFoundException
import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.resources.extensions.dtoToSchema
import io.github.emikaelsilveira.resources.extensions.toUserDomain
import io.github.emikaelsilveira.resources.repositories.schemas.AddressSchema
import io.github.emikaelsilveira.resources.repositories.schemas.UserSchema
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.LocalDateTime
import javax.sql.DataSource

class UserRepositoryImpl(dataSource: DataSource) : UserRepository {

    init {
        transaction(Database.connect(dataSource)) {
            SchemaUtils.create(UserSchema)
        }
    }

    override fun getAll() = transaction {
        (UserSchema leftJoin AddressSchema).selectAll().map { it.toUserDomain() }
    }

    override fun getOne(id: Long) = transaction {
        (UserSchema leftJoin AddressSchema).select { UserSchema.id eq id }.map { it.toUserDomain() }.firstOrNull()
            ?: throw NotFoundException(id.toString())
    }

    override fun create(userDTO: UserDTO) = transaction {
        val userId = UserSchema.insert {
            dtoToSchema(it, userDTO)
            it[createdAt] = LocalDateTime.now().toDateTime()
        } get UserSchema.id
        (UserSchema leftJoin AddressSchema).select { UserSchema.id eq userId }.map { it.toUserDomain() }.first()
    }

    override fun update(id: Long, userDTO: UserDTO) = transaction {
        UserSchema.update({ UserSchema.id eq id }) {
            dtoToSchema(it, userDTO)
            it[updatedAt] = LocalDateTime.now().toDateTime()
        }.let {
            (UserSchema leftJoin AddressSchema).select { UserSchema.id eq id }.map { it.toUserDomain() }.first()
        }
    }

    override fun delete(id: Long) = transaction {
        UserSchema.deleteWhere { UserSchema.id eq id }.let { Unit }
    }
}
