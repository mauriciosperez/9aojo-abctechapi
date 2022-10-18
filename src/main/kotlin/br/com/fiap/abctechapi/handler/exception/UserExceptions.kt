package br.com.fiap.abctechapi.handler.exception

import org.springframework.http.HttpStatus

object UserExceptions {
    object UserNotFound : GenericBackendException(
        "Failed to login",
        "User not found",
        HttpStatus.NOT_FOUND
    )
}
