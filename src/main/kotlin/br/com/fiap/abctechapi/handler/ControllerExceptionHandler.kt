package br.com.fiap.abctechapi.handler

import br.com.fiap.abctechapi.handler.exception.GenericBackendException
import br.com.fiap.abctechapi.handler.exception.UserExceptions
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.Date

@ControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(GenericBackendException::class)
    fun <T : GenericBackendException> defaultErrorHandler(exception: T) =
        ResponseEntity(
            ErrorMessageResponse(
                message = exception.message ?: "",
                description = exception.description,
                timestamp = Date(),
                statusCode = exception.status.value()
            ),
            exception.status
        )

    @ExceptionHandler(BadCredentialsException::class, InternalAuthenticationServiceException::class)
    fun badCredentialsHandler(): ResponseEntity<ErrorMessageResponse> =
        defaultErrorHandler(UserExceptions.FailedToLogin)

    @ExceptionHandler(Exception::class)
    fun genericErrorHandler(exception: Exception): ResponseEntity<ErrorMessageResponse> =
        defaultErrorHandler(GenericBackendException.UnknownError)
}
