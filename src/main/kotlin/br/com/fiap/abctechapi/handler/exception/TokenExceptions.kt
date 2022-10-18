package br.com.fiap.abctechapi.handler.exception

import org.springframework.http.HttpStatus

object TokenExceptions {
    object InvalidToken : GenericBackendException(
        message = "Invalid Token",
        description = "There was an error validating token",
        status = HttpStatus.BAD_REQUEST
    )
}
