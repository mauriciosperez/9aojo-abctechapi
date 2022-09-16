package br.com.fiap.abctechapi.handler

import java.util.*

data class ErrorMessageResponse(
    val statusCode: Int = 0,
    val timestamp: Date = Date(),
    val message: String = "",
    val description: String = ""
)