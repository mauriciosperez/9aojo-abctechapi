package br.com.fiap.abctechapi.handler.exception

import org.springframework.http.HttpStatus

object UserExceptions {

    private const val LOGIN_MESSAGE = "Falha no login"
    private const val SIGNUP_MESSAGE = "Falha no cadastro"
    private const val FAILED_TO_LOGIN = "Usuário ou senha incorreto"
    private const val USERNAME_NOT_SENT = "Nome de usuário não enviado"
    private const val PASSWORD_NOT_SENT = "Senha não enviada"
    private const val EMAIL_NOT_SENT = "Email não enviado"
    private const val USERNAME_TAKEN = "Esse usuário já foi selecionado"
    private const val EMAIL_TAKEN = "Esse email já doi selecionado"
    object FailedToLogin : GenericBackendException(
        message = LOGIN_MESSAGE,
        description = FAILED_TO_LOGIN,
        status = HttpStatus.NOT_FOUND
    )

    object UsernameNotSent : GenericBackendException(
        message = LOGIN_MESSAGE,
        description = USERNAME_NOT_SENT,
        status = HttpStatus.BAD_REQUEST
    )

    object PasswordNotSent : GenericBackendException(
        message = LOGIN_MESSAGE,
        description = PASSWORD_NOT_SENT,
        status = HttpStatus.BAD_REQUEST
    )

    object EmailNotSent : GenericBackendException(
        message = LOGIN_MESSAGE,
        description = EMAIL_NOT_SENT,
        status = HttpStatus.BAD_REQUEST
    )

    object UsernameTaken : GenericBackendException(
        message = SIGNUP_MESSAGE,
        description = USERNAME_TAKEN,
        status = HttpStatus.BAD_REQUEST
    )

    object EmailTaken : GenericBackendException(
        message = SIGNUP_MESSAGE,
        description = EMAIL_TAKEN,
        status = HttpStatus.BAD_REQUEST
    )
}
