package io.github.emikaelsilveira.componenttest

import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import io.github.emikaelsilveira.application.config.AppConfig
import io.github.emikaelsilveira.utils.builders.UserDTOBuilder
import io.github.emikaelsilveira.utils.components.DataBaseComponent
import io.github.emikaelsilveira.utils.extensions.asJsonToObject
import io.javalin.Javalin
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.jetty.http.HttpStatus.OK_200
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.json.JSONObject

object UserTest : Spek({

    lateinit var app: Javalin
    val url = "http://localhost:7000"

    describe("#UserTest") {

        beforeGroup { DataBaseComponent.init() }

        beforeEachTest {
            DataBaseComponent.clearDatabase()
            app = AppConfig().setup()
        }

        afterEachTest { app.stop() }

        afterGroup { DataBaseComponent.close() }

        describe("GET /users") {
            it("should return status 200 and an list of users") {
                DataBaseComponent.insertUser(UserDTOBuilder.build { addressDTO = null })

                val response = "$url/users".httpGet().response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
                String(response.data).also {
                    assertThat(it).isNotNull()
                }
            }

            it("should return status 200 and an empty list") {
                val response = "$url/users".httpGet().response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
                String(response.data).also {
                    assertThat(it).isEqualTo("[]")
                }
            }

            it("should return status 200 and accept a user") {
                val json = "accept-user.json".asJsonToObject()

                val response = "$url/users".httpPost()
                    .header("Content-Type", "application/json")
                    .body(json.toString())
                    .response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
                JSONObject(String(response.data)).also {
                    assertThat(it).isNotNull
                    assertThat(it["id"]).isNotNull
                    assertThat(it["create_at"]).isNotNull
                }
            }
        }

        describe("GET /users/:id") {
            it("should return status 200 an a User with id 1") {
                DataBaseComponent.insertUser(UserDTOBuilder.build { addressDTO = null })

                val response = "$url/users/1".httpGet().response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
                JSONObject(String(response.data)).also {
                    assertThat(it).isNotNull
                    assertThat(it["id"]).isEqualTo(1)
                }
            }
        }

        describe("DELETE /users/:id") {
            it("should return status 200 and delete an user") {
                DataBaseComponent.insertUser(UserDTOBuilder.build { addressDTO = null })

                val response = "$url/users/1".httpDelete().response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
            }
        }

        describe("PUT /users/:id") {
            it("should return status 200 and update an user") {
                DataBaseComponent.insertUser(UserDTOBuilder.build { addressDTO = null })
                val json = "update-user.json".asJsonToObject()

                val response = "$url/users/1".httpPut()
                    .header("Content-Type", "application/json")
                    .body(json.toString())
                    .response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
                JSONObject(String(response.data)).also {
                    assertThat(it).isNotNull
                    assertThat(it["id"]).isNotNull
                    assertThat(it["address"]).isNotNull
                    assertThat(it["update_at"]).isNotNull
                }
            }
        }
    }
})
