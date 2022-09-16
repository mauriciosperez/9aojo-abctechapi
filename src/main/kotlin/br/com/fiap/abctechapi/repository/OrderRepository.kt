package br.com.fiap.abctechapi.repository

import br.com.fiap.abctechapi.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>