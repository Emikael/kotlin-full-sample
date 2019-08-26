package io.github.emikaelsilveira.resource.extensions

import io.javalin.http.Context

fun Context.paramAsLong(pathParam: String) = this.pathParam(pathParam).toLong()
