package io.github.emikaelsilveira.tests

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import io.github.emikaelsilveira.application.config.AppConfig
import io.github.emikaelsilveira.tests.components.DataBaseComponent
import io.javalin.Javalin
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400
import org.eclipse.jetty.http.HttpStatus.OK_200
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.json.JSONObject

object AddressTest : Spek({

    lateinit var app: Javalin
    val url = "http://localhost:7000"

    describe("#AddressTest") {

        beforeGroup { DataBaseComponent.init() }

        beforeEachTest {
            DataBaseComponent.clearDatabase()
            app = AppConfig().setup()
        }

        afterEachTest { app.stop() }

        afterGroup { DataBaseComponent.close() }

        describe("POST /address") {
            it("should return status 200 and an accepted Address") {
                val cep = "88708-001"
                val response = "$url/address/$cep".httpPost().response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
                JSONObject(String(response.data)).also {
                    assertThat(it).isNotNull
                    assertThat(it["cep"]).isEqualTo(cep)
                }
            }

            it("should return status 400 when not find a CEP") {
                val response = "$url/address/erro".httpPost().response().second

                assertThat(BAD_REQUEST_400).isEqualTo(response.statusCode)
                String(response.data).also {
                    assertThat(it).isNotNull()
                }
            }
        }

        describe("GET /address") {
            it("should return status 200 and a list of address") {
                DataBaseComponent.insertAddress()

                val response = "$url/address".httpGet().response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
                String(response.data).also {
                    assertThat(it).isNotNull()
                }
            }

            it("should return status 200 and a empty list") {
                val response = "$url/address".httpGet().response().second

                assertThat(OK_200).isEqualTo(response.statusCode)
                String(response.data).also {
                    assertThat(it).isEqualTo("[]")
                }
            }
        }
    }
})
