package io.github.emikaelsilveira.resources.repositories

import io.github.emikaelsilveira.domain.entities.Address
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.resources.extensions.dtoToSchema
import io.github.emikaelsilveira.resources.extensions.toAddressDomain
import io.github.emikaelsilveira.resources.repositories.schemas.AddressSchema
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.LocalDateTime
import javax.sql.DataSource

class AddressRepositoryImpl(dataSource: DataSource) : AddressRepository {

    init {
        transaction(Database.connect(dataSource)) {
            SchemaUtils.create(AddressSchema)
        }
    }

    override fun getByCep(cep: String) = transaction {
        AddressSchema.select { AddressSchema.cep eq cep }.map { it.toAddressDomain() }.firstOrNull()
    }

    override fun create(address: Address) = transaction {
        val addressId = AddressSchema.insert {
            dtoToSchema(it, address)
            it[createdAt] = LocalDateTime.now().toDateTime()
        } get AddressSchema.id
        AddressSchema.select { AddressSchema.id eq addressId }.map { it.toAddressDomain() }.first()
    }

    override fun update(id: Long, address: Address) = transaction {
        AddressSchema.update({ AddressSchema.id eq id }) {
            dtoToSchema(it, address)
            it[updatedAt] = LocalDateTime.now().toDateTime()
        }.let {
            AddressSchema.select { AddressSchema.id eq id }.map { it.toAddressDomain() }.first()
        }
    }

    override fun getAll() = transaction {
        AddressSchema.selectAll().map { it.toAddressDomain() }
    }
}
