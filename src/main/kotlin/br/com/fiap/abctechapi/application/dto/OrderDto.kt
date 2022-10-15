package br.com.fiap.abctechapi.application.dto

data class OrderDto(
    val operatorId: Long = 0L,
    val assists: List<Long> = listOf(),
    val start: OrderLocationDto = OrderLocationDto(),
    val end: OrderLocationDto = OrderLocationDto()
)
