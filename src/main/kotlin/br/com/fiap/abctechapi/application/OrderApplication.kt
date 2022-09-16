package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.OrderDto

interface OrderApplication {
    fun createOrder(orderDto: OrderDto)
}