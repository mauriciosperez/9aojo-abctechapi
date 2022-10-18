package br.com.fiap.abctechapi.application.dto

data class LoginResponseDto(
    var id: Long = 0,
    var username: String = "",
    var email: String = ""
)
