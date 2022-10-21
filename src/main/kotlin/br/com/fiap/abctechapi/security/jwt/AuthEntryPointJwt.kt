package br.com.fiap.abctechapi.security.jwt

import br.com.fiap.abctechapi.handler.ErrorMessageResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val description =
            if (authException.message == DEFAULT_ERROR) ERROR_MESSAGE
            else authException.message ?: GENERIC_ERROR_MESSAGE

        val body = ErrorMessageResponse(
            message = MESSAGE,
            description = description,
            timestamp = Date(),
            statusCode = HttpServletResponse.SC_UNAUTHORIZED
        )
        ObjectMapper().writeValue(response.outputStream, body)
    }

    companion object {
        private const val MESSAGE = "Usuário não autorizado"
        private const val DEFAULT_ERROR = "Full authentication is required to access this resource"
        private const val ERROR_MESSAGE = "Autenticação é necessária pra acessar esse recurso"
        private const val GENERIC_ERROR_MESSAGE = "Erro inesperado :(. Tente novamente mais tard"
    }
}
