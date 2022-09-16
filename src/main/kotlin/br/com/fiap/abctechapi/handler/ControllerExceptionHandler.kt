package br.com.fiap.abctechapi.handler

import br.com.fiap.abctechapi.handler.exception.GenericBackendException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

@ControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(GenericBackendException::class)
    fun <T : GenericBackendException> defaultErrorHandler (exception: T) =
        ResponseEntity(
            ErrorMessageResponse(
                message = exception.message ?: "",
                description = exception.description,
                timestamp = Date(),
                statusCode = exception.status.value()
            ),
            exception.status
        )

    @ExceptionHandler(Exception::class)
    fun genericErrorHandler(): ResponseEntity<ErrorMessageResponse> =
        defaultErrorHandler(GenericBackendException.UNKNOWN_ERROR)
}