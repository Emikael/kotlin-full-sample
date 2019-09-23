package io.github.emikaelsilveira.tests.extensions

import org.json.JSONObject

fun String.payload(): String = javaClass.getResource("/samples/payloads/$this").readText()

fun String.asJsonToObject() = JSONObject(this.payload())
