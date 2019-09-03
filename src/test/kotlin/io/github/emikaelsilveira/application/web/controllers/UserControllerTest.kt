package io.github.emikaelsilveira.application.web.controllers

import io.github.emikaelsilveira.domain.entities.UserDTO
import io.github.emikaelsilveira.domain.services.UserService
import io.github.emikaelsilveira.environment.createUser
import io.github.emikaelsilveira.environment.createUser2
import io.javalin.http.Context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class UserControllerTest {

    private val service = mockk<UserService>()
    private val context = mockk<Context>()

    private val controller = UserController(service)

    companion object {
        private const val USER_ID = "id"
        private const val ID = "1"
    }

    @AfterEach
    fun down() = confirmVerified(service, context)

    @Test
    fun `should get a list of users`() {
        every { service.getAll() } returns listOf(createUser())

        val allUsers = controller.getAll()

        assertThat(allUsers).isNotNull
        assertThat(allUsers).isNotEmpty
        assertThat(allUsers).isEqualTo(listOf(createUser()))
        verify { service.getAll() }
    }

    @Test
    fun `should get a user by id`() {
        every { context.pathParam(USER_ID) } returns ID
        every { service.getOne(ID.toLong()) } returns createUser()

        val user = controller.getOne(context)

        assertThat(user).isNotNull
        assertThat(user).isEqualTo(createUser())
        verify {
            context.pathParam(USER_ID)
            service.getOne(ID.toLong())
        }
    }

    @Test
    fun `should create a new user`() {
        val objectUser = createUser()
        every { context.bodyAsClass(UserDTO::class.java) } returns objectUser
        every { service.create(objectUser) } returns createUser2()

        val user = controller.create(context)

        assertThat(user).isNotNull
        assertThat(user).isEqualTo(createUser2())
        verify {
            context.bodyAsClass(UserDTO::class.java)
            service.create(objectUser)
        }
    }

    @Test
    fun `should update a user`() {
        val objectUser = createUser()
        every { context.pathParam(USER_ID) } returns ID
        every { context.bodyAsClass(UserDTO::class.java) } returns objectUser
        every { service.update(ID.toLong(), objectUser) } returns createUser2()

        val user = controller.update(context)

        assertThat(user).isNotNull
        assertThat(user).isEqualTo(createUser2())
        verify {
            context.pathParam(USER_ID)
            context.bodyAsClass(UserDTO::class.java)
            service.update(ID.toLong(), objectUser)
        }
    }

    @Test
    fun `should delete a user`() {
        every { context.pathParam(USER_ID) } returns ID
        every { service.delete(ID.toLong()) } returns Unit

        controller.delete(context)

        verify {
            context.pathParam(USER_ID)
            service.delete(ID.toLong())
        }
    }
}
