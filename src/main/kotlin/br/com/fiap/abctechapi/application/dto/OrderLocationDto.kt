package br.com.fiap.abctechapi.application.dto

import java.util.Date

data class OrderLocationDto(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val date: Date = Date()
)
