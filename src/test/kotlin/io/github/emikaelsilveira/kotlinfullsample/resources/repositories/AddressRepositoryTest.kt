package io.github.emikaelsilveira.kotlinfullsample.resources.repositories

import io.github.emikaelsilveira.resources.repositories.AddressRepositoryImpl
import io.github.emikaelsilveira.utils.builders.AddressDTOBuilder
import io.github.emikaelsilveira.utils.components.DataBaseComponent
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object AddressRepositoryTest : Spek({

    lateinit var repository: AddressRepositoryImpl
    val address = AddressDTOBuilder.build()

    describe("#AddressRepository") {

        beforeGroup {
            DataBaseComponent.init()
            repository = AddressRepositoryImpl(DataBaseComponent.getDataSource())
        }

        beforeEachTest { DataBaseComponent.clearDatabase() }

        afterGroup { DataBaseComponent.close() }

        describe("Create Address") {
            it("should create an address on database") {
                val result = repository.create(address)

                assertThat(result).isNotNull
                assertThat(result.id).isNotNull()
                assertThat(result.createAt).isNotNull()
                assertThat(result.updateAt).isNull()
            }
        }

        describe("Get address by CEP") {
            it("should return an address by cep") {
                val addressSaved = repository.create(address)
                assertThat(addressSaved).isNotNull

                val result = repository.getByCep(addressSaved.cep)

                assertThat(result).isNotNull
                assertThat(result?.id).isEqualTo(addressSaved.id)
                assertThat(result?.cep).isEqualTo(addressSaved.cep)
            }

            it("should return null when fetching an address") {
                val result = repository.getByCep(address.cep)

                assertThat(result).isNull()
            }
        }

        describe("Update address") {
            it("should update an address on database") {
                val cep = "88708-003"
                val addressSaved = repository.create(address)
                assertThat(addressSaved).isNotNull

                val result = repository.update(addressSaved.id!!, addressSaved.copy(cep = cep))

                assertThat(result).isNotNull
                assertThat(result.updateAt).isNotNull()
                assertThat(result.id).isEqualTo(addressSaved.id!!)
                assertThat(result.cep).isEqualTo(cep)
            }
        }

        describe("Get all address") {
            it("should returns all address of database") {
                val addressSaved = repository.create(address)
                assertThat(addressSaved).isNotNull

                val result = repository.getAll()

                assertThat(result).isNotNull
                assertThat(result).size().isOne
                assertThat(result).first().isEqualTo(addressSaved)
            }
        }
    }
})
