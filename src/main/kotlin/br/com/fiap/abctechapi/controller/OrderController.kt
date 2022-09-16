package br.com.fiap.abctechapi.controller

import br.com.fiap.abctechapi.application.OrderApplication
import br.com.fiap.abctechapi.application.dto.OrderDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController @Autowired constructor(private val orderApplication: OrderApplication) {

    @PostMapping
    fun createOrder(@RequestBody orderDto: OrderDto): ResponseEntity<Any> {
        orderApplication.createOrder(orderDto)
        return ResponseEntity.ok().build()
    }
}
