package br.com.fiap.abctechapi.application.dto

data class OrderDto(
    val operatorId: Long,
    val assists: List<Long>,
    val start: OrderLocationDto,
    val end: OrderLocationDto,
)