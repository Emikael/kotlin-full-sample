package io.github.emikaelsilveira.resource.repositories.schemas

import io.github.emikaelsilveira.resource.commons.DEFAULT_VARCHAR_LENGTH
import org.jetbrains.exposed.sql.Table

object AddressSchema : Table("address") {
    val id = long("id").primaryKey().autoIncrement()
    val cep = varchar("cep", DEFAULT_VARCHAR_LENGTH)
    val street = varchar("street", DEFAULT_VARCHAR_LENGTH)
    val complement = varchar("complement", DEFAULT_VARCHAR_LENGTH).nullable()
    val neighborhood = varchar("neighborhood", DEFAULT_VARCHAR_LENGTH)
    val city = varchar("city", DEFAULT_VARCHAR_LENGTH)
    val state = varchar("state", DEFAULT_VARCHAR_LENGTH);
    val ibge = long("ibge")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at").nullable()
}
