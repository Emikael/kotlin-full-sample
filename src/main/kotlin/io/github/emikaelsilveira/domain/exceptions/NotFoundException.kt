package io.github.emikaelsilveira.domain.exceptions

import java.lang.RuntimeException

class NotFoundException(id: String) : RuntimeException("Object with identifier $id was not found.")
