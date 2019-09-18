package io.github.emikaelsilveira.resources.repositories

import io.github.emikaelsilveira.builders.AddressDTOBuilder
import io.github.emikaelsilveira.builders.UserDTOBuilder
import io.github.emikaelsilveira.components.DataBaseComponent
import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.resources.extensions.dtoToSchema
import io.github.emikaelsilveira.resources.repositories.schemas.AddressSchema
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.joda.time.LocalDateTime

object UserRepositoryTest : Spek({

    lateinit var repository: UserRepositoryImpl
    val user = UserDTOBuilder.build()

    describe("#UserRepository") {

        beforeGroup {
            DataBaseComponent.init()
            repository = UserRepositoryImpl(DataBaseComponent.getDataSource())
        }
        beforeEachTest { DataBaseComponent.clearDatabase() }
        afterGroup { DataBaseComponent.close() }

        describe("Create User") {
            it("should create an user on database") {
                insertAddress()

                val result = repository.create(user)

                assertThat(result).isNotNull
                assertThat(result.id).isNotNull()
                assertThat(result.createAt).isNotNull()
                assertThat(result.updateAt).isNull()
            }
        }

        describe("Get all users") {
            it("should returns all users of database") {
                insertAddress()

                val userSaved = repository.create(user)
                assertThat(userSaved).isNotNull

                val result = repository.getAll()

                assertThat(result).isNotNull
                assertThat(result).size().isOne
                assertThat(result).first().isEqualTo(userSaved)
            }
        }

        describe("Get an User") {
            it("should return an User by ID") {
                insertAddress()

                val userSaved = repository.create(user)
                assertThat(userSaved).isNotNull

                val result = repository.getOne(userSaved.id!!)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(userSaved)
            }
        }

        describe("Update User") {
            it("should update an user on database") {
                val name = "Johnny Bravo"
                insertAddress()

                val userSaved = repository.create(user)
                assertThat(userSaved).isNotNull

                val result = repository.update(userSaved.id!!, userSaved.copy(name = name))

                assertThat(result).isNotNull
                assertThat(result.id).isEqualTo(userSaved.id!!)
                assertThat(result.name).isEqualTo(name)
            }
        }

        describe("Delete User") {
            it("should delete an user on database") {
                insertAddress()

                val userSaved = repository.create(user)
                assertThat(userSaved).isNotNull

                val users = repository.getAll()
                assertThat(users).size().isOne

                repository.delete(userSaved.id!!)

                val usersDatabase = repository.getAll()
                assertThat(usersDatabase).isNullOrEmpty()
            }
        }
    }
})

private fun insertAddress(addressDTO: AddressDTO = AddressDTOBuilder.build()) {
    transaction {
        AddressSchema.insert {
            dtoToSchema(it, addressDTO)
            it[createdAt] = LocalDateTime.now().toDateTime()
        }
    }
}
