package br.com.fiap.abctechapi.application.impl

import br.com.fiap.abctechapi.application.OrderApplication
import br.com.fiap.abctechapi.application.dto.OrderDto
import br.com.fiap.abctechapi.application.dto.OrderLocationDto
import br.com.fiap.abctechapi.model.Order
import br.com.fiap.abctechapi.model.OrderLocation
import br.com.fiap.abctechapi.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderApplicationImpl @Autowired constructor(private val service: OrderService) : OrderApplication {
    override fun createOrder(orderDto: OrderDto) {
        val order = Order(
            operatorId = orderDto.operatorId,
            startOrderLocation = getOrderLocationFromOrderLocationDro(orderDto.start),
            endOrderLocation = getOrderLocationFromOrderLocationDro(orderDto.end)
        )

        service.saveOrder(order, assists = orderDto.assists)
    }

    companion object {
        private fun getOrderLocationFromOrderLocationDro(orderLocationDto: OrderLocationDto): OrderLocation {
            return OrderLocation(
                latitude = orderLocationDto.latitude,
                longitude = orderLocationDto.longitude,
                date = orderLocationDto.date
            )
        }
    }
}
