package br.com.fiap.abctechapi.controller

import br.com.fiap.abctechapi.application.OrderApplication
import br.com.fiap.abctechapi.application.dto.OrderDto
import br.com.fiap.abctechapi.application.dto.OrderResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order")
class OrderController @Autowired constructor(private val orderApplication: OrderApplication) {

    @PostMapping
    fun createOrder(@RequestBody orderDto: OrderDto): ResponseEntity<Any> {
        orderApplication.createOrder(orderDto)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/operator/{operatorId}")
    fun listOrderByOperator(@PathVariable operatorId: Long): ResponseEntity<List<OrderResponseDto>> =
        ResponseEntity.ok(orderApplication.listOrderByOperatorId(operatorId))
}
