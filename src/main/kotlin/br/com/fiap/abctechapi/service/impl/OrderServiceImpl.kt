package br.com.fiap.abctechapi.service.impl

import br.com.fiap.abctechapi.handler.exception.AssistExceptions
import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.model.Order
import br.com.fiap.abctechapi.repository.AssistanceRepository
import br.com.fiap.abctechapi.repository.OrderRepository
import br.com.fiap.abctechapi.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val assistanceRepository: AssistanceRepository
) : OrderService {
    override fun saveOrder(order: Order, assists: List<Long>) {
        val assistances = arrayListOf<Assistance>()

        try {
            assists.forEach { item ->
                val assistance = assistanceRepository.findById(item).orElseThrow()
                assistances.add(assistance)
            }

            order.assists = assistances

            when {
                !order.hasMinAssists() -> throw AssistExceptions.MinAssistsException
                order.exceedsMaxAssists() -> throw AssistExceptions.MaxAssistsException
                else -> { orderRepository.save(order) }
            }
        } catch (error: NoSuchElementException) {
            throw AssistExceptions.InvalidAssistException
        }
    }

    override fun listOrderByOperator(operatorId: Long): List<Order> = listOf()
}
