package br.com.fiap.abctechapi.handler.exception

import org.springframework.http.HttpStatus

open class GenericBackendException(
    message: String,
    val description: String,
    val status: HttpStatus
) : RuntimeException(message) {
    object UNKNOWN_ERROR : GenericBackendException(
        "Unknown Error",
        "Unknown error: try again later",
        HttpStatus.SERVICE_UNAVAILABLE
    )
}