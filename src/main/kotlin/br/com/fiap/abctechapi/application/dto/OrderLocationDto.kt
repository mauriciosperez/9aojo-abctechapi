package br.com.fiap.abctechapi.application.dto

import java.util.Date

data class OrderLocationDto(
    val latitude: Double,
    val longitude: Double,
    val date: Date
)
