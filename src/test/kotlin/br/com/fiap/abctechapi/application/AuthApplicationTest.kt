package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.LoginDto
import br.com.fiap.abctechapi.application.dto.LoginResponseDto
import br.com.fiap.abctechapi.application.dto.SignUpDto
import br.com.fiap.abctechapi.handler.exception.UserExceptions
import br.com.fiap.abctechapi.model.User
import br.com.fiap.abctechapi.model.UserDetailsImpl
import br.com.fiap.abctechapi.repository.UserRepository
import br.com.fiap.abctechapi.security.jwt.JwtUtils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.assertEquals

class AuthApplicationTest {
    @MockK
    lateinit var repository: UserRepository

    @MockK
    lateinit var authenticationManager: AuthenticationManager

    @MockK
    lateinit var authentication: Authentication

    @MockK
    private lateinit var jwtUtils: JwtUtils

    private val encoder: PasswordEncoder = BCryptPasswordEncoder()
    private lateinit var application: AuthApplication

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        application = AuthApplication(
            authenticationManager = authenticationManager,
            userRepository = repository,
            encoder = encoder,
            jwtUtils = jwtUtils
        )
    }

    @Test
    fun `create account with empty username`() {
        val body = SignUpDto(
            username = "",
            email = "teste@teste.com",
            password = "1234"
        )

        assertThrows<UserExceptions.UsernameNotSent> { application.signUp(body) }
    }

    @Test
    fun `create account with empty email`() {
        val body = SignUpDto(
            username = "username",
            email = "",
            password = "1234"
        )

        assertThrows<UserExceptions.EmailNotSent> { application.signUp(body) }
    }

    @Test
    fun `create account with empty password`() {
        val body = SignUpDto(
            username = "username",
            email = "teste@teste.com",
            password = ""
        )

        assertThrows<UserExceptions.PasswordNotSent> { application.signUp(body) }
    }

    @Test
    fun `create account with taken username`() {
        val body = SignUpDto(
            username = "username",
            email = "teste@teste.com",
            password = "1234"
        )

        every { repository.existsByUsername(body.username) } returns true

        assertThrows<UserExceptions.UsernameTaken> { application.signUp(body) }
    }

    @Test
    fun `create account with taken email`() {
        val body = SignUpDto(
            username = "username",
            email = "teste@teste.com",
            password = "1234"
        )

        every { repository.existsByUsername(body.username) } returns false
        every { repository.existsByEmail(body.email) } returns true

        assertThrows<UserExceptions.EmailTaken> { application.signUp(body) }
    }

    @Test
    fun `create account success`() {
        val body = SignUpDto(
            username = "username",
            email = "teste@teste.com",
            password = "1234"
        )

        every { repository.existsByUsername(body.username) } returns false
        every { repository.existsByEmail(body.email) } returns false

        every { repository.save(any()) } returns signUpDtoToUser(body)

        val result = application.signUp(body)
        assertEquals(result.message, REGISTERED_USER)
    }

    private fun signUpDtoToUser(dto: SignUpDto) = User(
        username = dto.username,
        email = dto.email,
        password = encoder.encode(dto.password)
    )

    @Test
    fun `login with empty username`() {
        val body = LoginDto(
            input = "",
            password = "1234"
        )

        assertThrows<UserExceptions.UsernameNotSent> { application.login(body) }
    }

    @Test
    fun `login with empty password`() {
        val body = LoginDto(
            input = "aa",
            password = ""
        )

        assertThrows<UserExceptions.PasswordNotSent> { application.login(body) }
    }

    @Test
    fun `login success`() {
        val body = LoginDto(
            input = "aa",
            password = "1234"
        )

        every { authentication.principal } returns userDetails
        every { authenticationManager.authenticate(any()) } returns authentication
        every { jwtUtils.generateJwtCookie(any()) } returns cookie

        val response = LoginResponseDto(
            id = userDetails.id,
            username = userDetails.username,
            email = userDetails.email,
            token = cookie.value
        )

        val (responseBody, token) = application.login(body)

        assertEquals(response, responseBody)
        assertEquals(cookie.toString(), token)
    }

    @Test
    fun `logout success`() {
        every { jwtUtils.getCleanJwtCookie() } returns ResponseCookie.from("abctech", "").build()

        val (response, token) = application.logout()

        assertEquals(SIGNED_OUT_MESSAGE, response.message)
        assertEquals("abctech=", token)
    }

    companion object {
        private const val REGISTERED_USER = "Usuário registrado com sucesso"
        private const val SIGNED_OUT_MESSAGE = "Usuário deslogado com sucesso"

        private val userDetails = UserDetailsImpl(
            1L,
            "username",
            "email@email.com",
            "1234",
            listOf(SimpleGrantedAuthority("USER"))
        )

        private val cookie = ResponseCookie.from("abctech", "jdsklada").build()
    }
}
