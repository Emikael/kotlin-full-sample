package io.github.emikaelsilveira.resource.repositories

import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.exceptions.AddressNotFoundException
import io.github.emikaelsilveira.domain.repositories.AddressRepository
import io.github.emikaelsilveira.resource.extensions.toAddressDomain
import io.github.emikaelsilveira.resource.repositories.schemas.AddressSchema
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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
            ?: throw AddressNotFoundException(cep)
    }

    override fun create(addressDTO: AddressDTO) = transaction {
        val addressId = AddressSchema.insert {
            it[cep] = addressDTO.cep
            it[street] = addressDTO.street
            it[complement] = addressDTO.complement
            it[neighborhood] = addressDTO.neighborhood
            it[city] = addressDTO.city
            it[state] = addressDTO.state
            it[ibge] = addressDTO.ibge
            it[createdAt] = LocalDateTime.now().toDateTime()
        } get AddressSchema.id
        addressDTO.copy(id = addressId)
    }

    override fun update(id: Long, addressDTO: AddressDTO) = transaction {
        AddressSchema.update({ AddressSchema.id eq id }) {
            it[cep] = addressDTO.cep
            it[street] = addressDTO.street
            it[complement] = addressDTO.complement
            it[neighborhood] = addressDTO.neighborhood
            it[city] = addressDTO.city
            it[state] = addressDTO.state
            it[ibge] = addressDTO.ibge
            it[updatedAt] = LocalDateTime.now().toDateTime()
        }.let {
            AddressSchema.select { AddressSchema.id eq id }.map { it.toAddressDomain() }.first()
        }
    }
}
