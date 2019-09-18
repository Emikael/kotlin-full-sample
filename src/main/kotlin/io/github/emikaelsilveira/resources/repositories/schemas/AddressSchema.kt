package io.github.emikaelsilveira.resources.repositories.schemas

import io.github.emikaelsilveira.resources.commons.DEFAULT_VARCHAR_LENGTH
import org.jetbrains.exposed.sql.Table

object AddressSchema : Table("address") {
    val id = long("id").primaryKey().autoIncrement()
    val cep = varchar("cep", DEFAULT_VARCHAR_LENGTH).uniqueIndex()
    val street = varchar("street", DEFAULT_VARCHAR_LENGTH).nullable()
    val complement = varchar("complement", DEFAULT_VARCHAR_LENGTH).nullable()
    val neighborhood = varchar("neighborhood", DEFAULT_VARCHAR_LENGTH).nullable()
    val city = varchar("city", DEFAULT_VARCHAR_LENGTH).nullable()
    val state = varchar("state", DEFAULT_VARCHAR_LENGTH).nullable()
    val ibge = long("ibge").nullable()
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at").nullable()
}
