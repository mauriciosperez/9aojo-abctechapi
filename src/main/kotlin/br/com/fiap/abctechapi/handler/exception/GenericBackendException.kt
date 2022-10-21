package br.com.fiap.abctechapi.handler.exception

import org.springframework.http.HttpStatus

open class GenericBackendException(
    message: String,
    val description: String,
    val status: HttpStatus
) : RuntimeException(message) {
    object UnknownError : GenericBackendException(
        message = UNKNOWN_ERROR,
        description = UNKNOWN_ERROR_DESCRIPTION,
        status = HttpStatus.SERVICE_UNAVAILABLE
    )

    companion object {
        private const val UNKNOWN_ERROR = "Erro desconhecido :("
        private const val UNKNOWN_ERROR_DESCRIPTION = "Tente novamente mais tarde"
    }
}
