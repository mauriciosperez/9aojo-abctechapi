package br.com.fiap.abctechapi.handler.exception

import org.springframework.http.HttpStatus

object AssistExceptions {
    private const val MESSAGE = "Invalid Assists"
    private const val MAX_ASSISTS_DESCRIPTION = "Número máximo de assistências é 15"
    private const val MIN_ASSISTS_DESCRIPTION = "Número máximo de assistências é 15"
    private const val INVALID_ASSIST_DESCRIPTION = "Assistência inválida"

    object MinAssistsException : GenericBackendException(
        MESSAGE,
        MIN_ASSISTS_DESCRIPTION,
        HttpStatus.BAD_REQUEST
    )

    object MaxAssistsException : GenericBackendException(
        MESSAGE,
        MAX_ASSISTS_DESCRIPTION,
        HttpStatus.BAD_REQUEST
    )

    object InvalidAssistException : GenericBackendException(
        MESSAGE,
        INVALID_ASSIST_DESCRIPTION,
        HttpStatus.BAD_REQUEST
    )
}