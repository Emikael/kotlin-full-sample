package io.github.emikaelsilveira.resources.extensions

import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.resources.repositories.schemas.AddressSchema
import io.github.emikaelsilveira.resources.repositories.schemas.UserSchema
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

fun ResultRow.toUserDomain() = UserDTO(
    id = this[UserSchema.id],
    name = this[UserSchema.name],
    email = this[UserSchema.email],
    phone = this[UserSchema.phone],
    addressDTO = this[UserSchema.cep]?.let { this.toAddressDomain() },
    createAt = this[UserSchema.createdAt].toLocalDateTime(),
    updateAt = this[UserSchema.updatedAt]?.toLocalDateTime()
)

fun ResultRow.toAddressDomain() = AddressDTO(
    id = this[AddressSchema.id],
    cep = this[AddressSchema.cep],
    street = this[AddressSchema.street],
    complement = this[AddressSchema.complement],
    neighborhood = this[AddressSchema.neighborhood],
    city = this[AddressSchema.city],
    ibge = this[AddressSchema.ibge],
    state = this[AddressSchema.state],
    createAt = this[AddressSchema.createdAt].toLocalDateTime(),
    updateAt = this[AddressSchema.updatedAt]?.toLocalDateTime()
)

fun AddressSchema.dtoToSchema(it: UpdateBuilder<Int>, addressDTO: AddressDTO) {
    it[cep] = addressDTO.cep
    it[street] = addressDTO.street
    it[complement] = addressDTO.complement
    it[neighborhood] = addressDTO.neighborhood
    it[city] = addressDTO.city
    it[state] = addressDTO.state
    it[ibge] = addressDTO.ibge
}

fun UserSchema.dtoToSchema(it: UpdateBuilder<Int>, userDTO: UserDTO) {
    it[name] = userDTO.name
    it[email] = userDTO.email
    it[phone] = userDTO.phone
    it[cep] = userDTO.addressDTO?.cep
}
