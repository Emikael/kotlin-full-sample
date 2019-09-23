package io.github.emikaelsilveira.tests.builders

import io.github.emikaelsilveira.domain.entities.AddressDTO
import io.github.emikaelsilveira.domain.entities.UserDTO
import org.joda.time.LocalDateTime

class UserDTOBuilder {

    var id = 1L
    var name = "Emikael Silveira"
    var email = "emikael.silveira@gmail.com"
    var phone = "(99) 99999-9999"
    var addressDTO: AddressDTO? = AddressDTOBuilder.build()
    private var createAt: LocalDateTime = LocalDateTime.now()
    private var updateAt: LocalDateTime = LocalDateTime.now()

    private fun build() = UserDTO(
        id = id,
        name = name,
        email = email,
        phone = phone,
        addressDTO = addressDTO,
        updateAt = updateAt,
        createAt = createAt
    )

    companion object {
        fun build(block: (UserDTOBuilder.() -> Unit)? = null) = when (block) {
            null -> UserDTOBuilder().build()
            else -> UserDTOBuilder().apply(block).build()
        }
    }
}
