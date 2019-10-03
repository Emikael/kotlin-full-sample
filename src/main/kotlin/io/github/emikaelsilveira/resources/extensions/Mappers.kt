package io.github.emikaelsilveira.resources.extensions

import io.github.emikaelsilveira.domain.entities.Address
import io.github.emikaelsilveira.domain.entities.User
import io.github.emikaelsilveira.resources.repositories.schemas.AddressSchema
import io.github.emikaelsilveira.resources.repositories.schemas.UserSchema
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

fun ResultRow.toUserDomain() = User(
    id = this[UserSchema.id],
    name = this[UserSchema.name],
    email = this[UserSchema.email],
    phone = this[UserSchema.phone],
    address = this[UserSchema.cep]?.let { this.toAddressDomain() },
    createAt = this[UserSchema.createdAt].toLocalDateTime(),
    updateAt = this[UserSchema.updatedAt]?.toLocalDateTime()
)

fun ResultRow.toAddressDomain() = Address(
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

fun AddressSchema.dtoToSchema(it: UpdateBuilder<Int>, address: Address) {
    it[cep] = address.cep
    it[street] = address.street
    it[complement] = address.complement
    it[neighborhood] = address.neighborhood
    it[city] = address.city
    it[state] = address.state
    it[ibge] = address.ibge
}

fun UserSchema.dtoToSchema(it: UpdateBuilder<Int>, user: User) {
    it[name] = user.name
    it[email] = user.email
    it[phone] = user.phone
    it[cep] = user.address?.cep
}
