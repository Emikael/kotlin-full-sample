package io.github.emikaelsilveira.resources.extensions

fun String.payload(): String = javaClass.getResource("/samples/payloads/$this").readText()
