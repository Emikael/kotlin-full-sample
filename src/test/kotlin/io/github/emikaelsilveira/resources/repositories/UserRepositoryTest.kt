package io.github.emikaelsilveira.resources.repositories

import io.github.emikaelsilveira.builders.AddressDTOBuilder
import io.github.emikaelsilveira.builders.UserDTOBuilder
import io.github.emikaelsilveira.components.DataBaseComponent
import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.entities.UserDTO
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
    lateinit var user: UserDTO
    val otherUser = UserDTOBuilder.build()

    describe("#UserRepository") {

        beforeGroup {
            DataBaseComponent.init()
            repository = UserRepositoryImpl(DataBaseComponent.getDataSource())
        }
        beforeEachTest {
            DataBaseComponent.clearDatabase()
            insertAddress()
            user = repository.create(otherUser)
        }
        afterGroup { DataBaseComponent.close() }

        describe("Create User") {
            it("should create an user on database") {
                assertThat(user).isNotNull
                assertThat(user.id).isNotNull()
                assertThat(user.createAt).isNotNull()
                assertThat(user.updateAt).isNull()
            }
        }

        describe("Get all users") {
            it("should returns all users of database") {
                val result = repository.getAll()

                assertThat(result).isNotNull
                assertThat(result).size().isOne
                assertThat(result).first().isEqualTo(user)
            }
        }

        describe("Get an User") {
            it("should return an User by ID") {
                val result = repository.getOne(user.id!!)

                assertThat(result).isNotNull
                assertThat(result).isEqualTo(user)
            }
        }

        describe("Update User") {
            it("should update an user on database") {
                val name = "Johnny Bravo"
                val result = repository.update(user.id!!, user.copy(name = name))

                assertThat(result).isNotNull
                assertThat(result.id).isEqualTo(user.id!!)
                assertThat(result.name).isEqualTo(name)
            }
        }

        describe("Delete User") {
            it("should delete an user on database") {
                val users = repository.getAll()
                assertThat(users).size().isOne

                repository.delete(user.id!!)

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
