package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.LoginDto
import br.com.fiap.abctechapi.application.dto.LoginResponseDto
import br.com.fiap.abctechapi.application.dto.MessageResponseDto
import br.com.fiap.abctechapi.application.dto.SignUpDto
import br.com.fiap.abctechapi.handler.exception.UserExceptions
import br.com.fiap.abctechapi.model.User
import br.com.fiap.abctechapi.model.UserDetailsImpl
import br.com.fiap.abctechapi.repository.UserRepository
import br.com.fiap.abctechapi.security.jwt.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthApplication @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {
    fun login(request: LoginDto): Pair<LoginResponseDto, String> {
        if (request.input.isEmpty()) throw UserExceptions.UsernameNotSent
        if (request.password.isEmpty()) throw UserExceptions.PasswordNotSent

        val authentication: Authentication = authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(request.input, request.password))

        SecurityContextHolder.getContext().authentication = authentication
        val userDetails = authentication.principal as UserDetailsImpl
        val jwtCookie = jwtUtils.generateJwtCookie(userDetails)
        val response = LoginResponseDto(
            id = userDetails.id,
            username = userDetails.username,
            email = userDetails.email,
            token = jwtCookie.value
        )
        return Pair(response, jwtCookie.toString())
    }

    fun signUp(request: SignUpDto): MessageResponseDto {
        if (request.username.isEmpty()) throw UserExceptions.UsernameNotSent
        if (request.password.isEmpty()) throw UserExceptions.PasswordNotSent
        if (request.email.isEmpty()) throw UserExceptions.EmailNotSent

        if (userRepository.existsByUsername(request.username)) throw UserExceptions.UsernameTaken
        if (userRepository.existsByEmail(request.email)) throw UserExceptions.EmailTaken

        val user = User(
            username = request.username,
            email = request.email,
            password = encoder.encode(request.password)
        )

        userRepository.save(user)

        return MessageResponseDto(message = REGISTERED_USER)
    }

    fun logout(): Pair<MessageResponseDto, String> {
        val cookie = jwtUtils.getCleanJwtCookie()
        return Pair(
            MessageResponseDto(message = SIGNED_OUT_MESSAGE),
            cookie.toString()
        )
    }

    companion object {
        private const val REGISTERED_USER = "Usuário registrado com sucesso"
        private const val SIGNED_OUT_MESSAGE = "Usuário deslogado com sucesso"
    }
}
