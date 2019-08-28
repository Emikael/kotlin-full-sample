package io.github.emikaelsilveira.resource.repositories

import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.domain.exceptions.NotFoundException
import io.github.emikaelsilveira.domain.repositories.UserRepository
import io.github.emikaelsilveira.resource.extensions.dtoToSchema
import io.github.emikaelsilveira.resource.extensions.toUserDomain
import io.github.emikaelsilveira.resource.repositories.schemas.AddressSchema
import io.github.emikaelsilveira.resource.repositories.schemas.UserSchema
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
        (UserSchema innerJoin AddressSchema).selectAll().map { it.toUserDomain() }
    }

    override fun getOne(id: Long) = transaction {
        (UserSchema innerJoin AddressSchema).select { UserSchema.id eq id }.map { it.toUserDomain() }.firstOrNull()
            ?: throw NotFoundException(id.toString())
    }

    override fun create(userDTO: UserDTO) = transaction {
        val userId = UserSchema.insert {
            dtoToSchema(it, userDTO)
            it[createdAt] = LocalDateTime.now().toDateTime()
        } get UserSchema.id
        userDTO.copy(id = userId)
    }

    override fun update(id: Long, userDTO: UserDTO) = transaction {
        UserSchema.update({ UserSchema.id eq id }) {
            dtoToSchema(it, userDTO)
            it[updatedAt] = LocalDateTime.now().toDateTime()
        }.let {
            (UserSchema innerJoin AddressSchema).select { UserSchema.id eq id }.map { it.toUserDomain() }.first()
        }
    }

    override fun delete(id: Long) = transaction {
        UserSchema.deleteWhere { UserSchema.id eq id }.let { Unit }
    }
}
