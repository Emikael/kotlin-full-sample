package io.github.emikaelsilveira.resource.extensions

import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.resource.repositories.schemas.AddressSchema
import io.github.emikaelsilveira.resource.repositories.schemas.UserSchema
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUserDomain() = UserDTO(
    id = this[UserSchema.id],
    name = this[UserSchema.name],
    email = this[UserSchema.email],
    phone = this[UserSchema.phone],
    addressDTO = this[UserSchema.addressId]?.let { this.toAddressDomain() },
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
