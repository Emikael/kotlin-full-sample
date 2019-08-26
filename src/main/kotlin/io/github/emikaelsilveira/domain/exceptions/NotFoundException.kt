package io.github.emikaelsilveira.domain.exceptions

import java.lang.RuntimeException

class UserNotFoundException(id: Long) : RuntimeException("User with identifier $id was not found.")

class AddressNotFoundException(cep: String) : RuntimeException("Address with cep $cep was not found")
