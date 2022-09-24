package br.com.fiap.abctechapi.service

import br.com.fiap.abctechapi.handler.exception.AssistExceptions
import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.model.Order
import br.com.fiap.abctechapi.repository.AssistanceRepository
import br.com.fiap.abctechapi.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val assistanceRepository: AssistanceRepository
) {
    fun saveOrder(order: Order, assists: List<Long>) {
        val assistances = arrayListOf<Assistance>()

        assists.forEach { item ->
            val assistance = assistanceRepository.findById(item)
            if (assistance.isPresent) assistances.add(assistance.get())
            else throw AssistExceptions.InvalidAssistException
        }

        order.assists = assistances

        when {
            !order.hasMinAssists() -> throw AssistExceptions.MinAssistsException
            order.exceedsMaxAssists() -> throw AssistExceptions.MaxAssistsException
            else -> { orderRepository.save(order) }
        }
    }

    fun listOrderByOperator(operatorId: Long): List<Order> = listOf()
}
