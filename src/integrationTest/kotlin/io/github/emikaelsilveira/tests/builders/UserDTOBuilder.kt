package io.github.emikaelsilveira.tests.builders

import io.github.emikaelsilveira.domain.entities.Address
import io.github.emikaelsilveira.domain.entities.User
import org.joda.time.LocalDateTime

class UserDTOBuilder {

    var id = 1L
    var name = "Emikael Silveira"
    var email = "emikael.silveira@gmail.com"
    var phone = "(99) 99999-9999"
    var address: Address? = AddressDTOBuilder.build()
    private var createAt: LocalDateTime = LocalDateTime.now()
    private var updateAt: LocalDateTime = LocalDateTime.now()

    private fun build() = User(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address,
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
