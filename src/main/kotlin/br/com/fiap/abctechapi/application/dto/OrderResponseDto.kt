package br.com.fiap.abctechapi.application.dto

data class OrderResponseDto(
    val id: Long,
    val operatorId: Long,
    val assists: List<AssistDto>,
    val start: OrderLocationDto,
    val end: OrderLocationDto
)
