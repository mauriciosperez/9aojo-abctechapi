package br.com.fiap.abctechapi.service

import br.com.fiap.abctechapi.model.Order
import kotlin.jvm.Throws

interface OrderService {
    @Throws(Exception::class)
    fun saveOrder(order: Order, assists: List<Long>)
    fun listOrderByOperator(operatorId: Long): List<Order>
}
