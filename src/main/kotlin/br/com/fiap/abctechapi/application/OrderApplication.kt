package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.AssistDto
import br.com.fiap.abctechapi.application.dto.OrderDto
import br.com.fiap.abctechapi.application.dto.OrderLocationDto
import br.com.fiap.abctechapi.application.dto.OrderResponseDto
import br.com.fiap.abctechapi.model.Assistance
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
            startOrderLocation = mapDtoToOrderLocation(orderDto.start),
            endOrderLocation = mapDtoToOrderLocation(orderDto.end)
        )

        service.saveOrder(order = order, assists = orderDto.assists)
    }

    fun listOrderByOperatorId(operatorId: Long): List<OrderResponseDto> =
        service.listOrderByOperator(operatorId).map { order ->
            OrderResponseDto(
                id = order.id,
                operatorId = order.operatorId,
                assists = order.assists.map(::mapAssistToDto),
                start = mapOrderLocationToDto(order.startOrderLocation),
                end = mapOrderLocationToDto(order.endOrderLocation)
            )
        }
    companion object {
        private fun mapDtoToOrderLocation(orderLocationDto: OrderLocationDto) =
            OrderLocation(
                latitude = orderLocationDto.latitude,
                longitude = orderLocationDto.longitude,
                date = orderLocationDto.date
            )

        private fun mapOrderLocationToDto(orderLocation: OrderLocation) =
            OrderLocationDto(
                latitude = orderLocation.latitude,
                longitude = orderLocation.longitude,
                date = orderLocation.date
            )

        private fun mapAssistToDto(assist: Assistance) =
            AssistDto(
                id = assist.id,
                name = assist.name,
                description = assist.description
            )
    }
}
