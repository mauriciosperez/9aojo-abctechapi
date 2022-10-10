package br.com.fiap.abctechapi.service

import br.com.fiap.abctechapi.handler.exception.AssistExceptions
import br.com.fiap.abctechapi.handler.exception.AssistExceptions.MaxAssistsException
import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.model.Order
import br.com.fiap.abctechapi.repository.AssistanceRepository
import br.com.fiap.abctechapi.repository.OrderRepository
import io.mockk.MockKAnnotations
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import java.util.Optional
import kotlin.collections.ArrayList
import kotlin.test.assertNotNull

@SpringBootTest
class OrderServiceTest {
    @MockK
    lateinit var orderRepository: OrderRepository

    @MockK
    lateinit var assistanceRepository: AssistanceRepository

    private lateinit var orderService: OrderService

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        orderService = OrderService(orderRepository, assistanceRepository)

        every {
            assistanceRepository.findById(any())
        } returns Optional.of(
            Assistance(
                id = 1L,
                name = "Teste",
                description = "Teste description:"
            )
        )

        every { orderRepository.save(any()) } returns Order()
    }

    @Test
    fun `non null service`() {
        assertNotNull(orderService)
    }

    @Test
    fun `create order with less than minimum`() {
        val newOrder = Order()
        newOrder.operatorId = 1234L

        assertThrows<AssistExceptions.MinAssistsException> { orderService.saveOrder(newOrder, listOf()) }
        verify { orderRepository.save(any()) wasNot called }
    }

    @Test
    fun `create successful order`() {
        val newOrder = Order()
        newOrder.operatorId = 1234L
        orderService.saveOrder(newOrder, generateMocksIds(5))

        verify(exactly = 1) { orderRepository.save(newOrder) }
    }

    @Test
    fun `create order with more than maximum`() {
        val newOrder = Order()
        newOrder.operatorId = 1234L

        assertThrows<MaxAssistsException> { orderService.saveOrder(newOrder, generateMocksIds(20)) }
        verify { orderRepository.save(any()) wasNot called }
    }

    private fun generateMocksIds(number: Int): List<Long> {
        val arrayList = ArrayList<Long>()
        for (i in 0 until number) {
            arrayList.add(Integer.toUnsignedLong(i))
        }
        return arrayList
    }
}
