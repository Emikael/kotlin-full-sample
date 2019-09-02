package io.github.emikaelsilveira.environment

import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.entities.UserDTO

fun createAddress() = AddressDTO(
    1,
    "88708-001",
    "Street test",
    "test complement",
    "center",
    "Tubarao",
    "SC",
    100303,
    null,
    null
)

fun createUser() = UserDTO(
    1,
    "Emikael Silveira",
    "emikael.silveira@gmail.com",
    "(48) 99955-3914",
    createAddress(),
    null,
    null
)

fun createUser2() = createUser().copy(id = 2, name = "Da Guarda")
