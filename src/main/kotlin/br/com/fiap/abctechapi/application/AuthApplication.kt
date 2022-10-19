package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.LoginDto
import br.com.fiap.abctechapi.application.dto.LoginResponseDto
import br.com.fiap.abctechapi.application.dto.MessageResponseDto
import br.com.fiap.abctechapi.application.dto.SignUpDto
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
        if (userRepository.existsByUsername(request.username)) {
            return MessageResponseDto(message = "Error: Username is already taken!")
        }

        if (userRepository.existsByEmail(request.email)) {
            return MessageResponseDto(message = "Error: Email is already in use!")
        }

        val user = User(
            username = request.username,
            email = request.email,
            password = encoder.encode(request.password)
        )

        userRepository.save(user)

        return MessageResponseDto(message = "User registered successfully!")
    }

    fun logout(): Pair<MessageResponseDto, String> {
        val cookie = jwtUtils.getCleanJwtCookie()
        return Pair(MessageResponseDto("You've been signed out!"), cookie.toString())
    }
}
