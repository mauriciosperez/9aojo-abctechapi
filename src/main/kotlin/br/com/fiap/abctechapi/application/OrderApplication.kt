package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.OrderDto
import br.com.fiap.abctechapi.application.dto.OrderLocationDto
import br.com.fiap.abctechapi.model.Order
import br.com.fiap.abctechapi.model.OrderLocation
import br.com.fiap.abctechapi.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderApplication @Autowired constructor(private val service: OrderService) {
    fun createOrder(orderDto: OrderDto) {
        val order = Order(
            operatorId = orderDto.operatorId,
            startOrderLocation = getOrderLocationFromOrderLocationDto(orderDto.start),
            endOrderLocation = getOrderLocationFromOrderLocationDto(orderDto.end)
        )

        service.saveOrder(order, assists = orderDto.assists)
    }

    companion object {
        private fun getOrderLocationFromOrderLocationDto(orderLocationDto: OrderLocationDto) =
            OrderLocation(
                latitude = orderLocationDto.latitude,
                longitude = orderLocationDto.longitude,
                date = orderLocationDto.date
            )
    }
}
