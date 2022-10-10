package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.AssistDto
import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.service.AssistanceService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AssistanceApplicationTest {
    @MockK
    lateinit var service: AssistanceService
    private lateinit var application: AssistanceApplication

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        application = AssistanceApplication(service)
    }

    @Test
    fun `convert empty list`() {
        every { service.getAssitanceList() } returns listOf()

        assertEquals(mutableListOf(), application.getAssists())
    }

    @Test
    fun `convert list`() {
        val list = listOf(
            Assistance(id = 1L, name = "Assistance 1", description = "Assistance 1 Desc"),
            Assistance(id = 2L, name = "Assistance 2", description = "Assistance 2 Desc"),
            Assistance(id = 3L, name = "Assistance 3", description = "Assistance 3 Desc")
        )

        val dtoList = mutableListOf(
            AssistDto(id = 1L, name = "Assistance 1", description = "Assistance 1 Desc"),
            AssistDto(id = 2L, name = "Assistance 2", description = "Assistance 2 Desc"),
            AssistDto(id = 3L, name = "Assistance 3", description = "Assistance 3 Desc")
        )

        every { service.getAssitanceList() } returns list
        assert(verifyLists(dtoList, application.getAssists()))
    }

    private fun verifyLists(
        expected: MutableList<AssistDto>,
        actual: MutableList<AssistDto>
    ): Boolean {
        if (expected.size != actual.size) return false

        expected.forEachIndexed { index, assistDto ->
            val item = actual[index]
            if (
                assistDto.id != item.id ||
                assistDto.name != item.name ||
                assistDto.description != item.description
            ) return false
        }

        return true
    }
}
