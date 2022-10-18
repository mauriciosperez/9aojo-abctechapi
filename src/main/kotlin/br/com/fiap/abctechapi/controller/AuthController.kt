package br.com.fiap.abctechapi.controller

import br.com.fiap.abctechapi.application.AuthApplication
import br.com.fiap.abctechapi.application.dto.LoginDto
import br.com.fiap.abctechapi.application.dto.LoginResponseDto
import br.com.fiap.abctechapi.application.dto.MessageResponseDto
import br.com.fiap.abctechapi.application.dto.SignUpDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AuthController @Autowired constructor(private val application: AuthApplication) {
    @PostMapping("/auth/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginDto): ResponseEntity<LoginResponseDto> {
        val (body, token) = application.login(loginRequest)
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, token).body(body)
    }

    @PostMapping("/auth/signup")
    fun registerUser(@RequestBody signUpRequest: SignUpDto): ResponseEntity<MessageResponseDto> =
        ResponseEntity.ok(application.signUp(signUpRequest))

    @PostMapping("/api/auth/signout")
    fun exitUser(): ResponseEntity<MessageResponseDto> {
        val (body, token) = application.logout()
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, token).body(body)
    }
}
