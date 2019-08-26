package io.github.emikaelsilveira.resource.repositories.schemas

import io.github.emikaelsilveira.resource.commons.DEFAULT_VARCHAR_LENGTH
import io.github.emikaelsilveira.resource.commons.DEFAULT_VARCHAR_PHONE
import org.jetbrains.exposed.sql.Table

object UserSchema : Table("users") {
    val id = long("id").primaryKey().autoIncrement()
    val name = varchar("name", DEFAULT_VARCHAR_LENGTH)
    val email = varchar("email", DEFAULT_VARCHAR_LENGTH)
    val phone = varchar("phone", DEFAULT_VARCHAR_PHONE).nullable()
    val addressId = long("addressid").references(AddressSchema.id).nullable()
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at").nullable()
}
