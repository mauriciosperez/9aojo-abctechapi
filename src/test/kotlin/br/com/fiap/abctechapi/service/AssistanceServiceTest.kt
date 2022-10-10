package br.com.fiap.abctechapi.service

import br.com.fiap.abctechapi.handler.exception.AssistExceptions
import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.repository.AssistanceRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class AssistanceServiceTest {
    @MockK
    lateinit var assistanceRepository: AssistanceRepository
    private lateinit var assistanceService: AssistanceService

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        assistanceService = AssistanceService(assistanceRepository)
    }

    @Test
    fun `lista assistencia disponiveis sucesso`() {
        val assistances = listOf(
            Assistance(id = 1L, name = "Mock 1", description = "Description 1"),
            Assistance(id = 2L, name = "Mock 2", description = "Description 2"),
            Assistance(id = 3L, name = "Mock 3", description = "Description 3")
        )

        every { assistanceRepository.findAll() } returns assistances

        var values: List<Assistance> = listOf()
        assertDoesNotThrow {
            values = assistanceService.getAssitanceList()
        }
        assertEquals(3, values.size)
        assertEquals(values[0], Assistance(1L, "Mock 1", "Description 1"))
        assertEquals(values[1], Assistance(2L, "Mock 2", "Description 2"))
    }

    @Test
    fun `lista assistencias disponiveis falha`() {
        every { assistanceRepository.findAll() } returns listOf()

        assertThrows<AssistExceptions.NoAssistFoundException> { assistanceService.getAssitanceList() }
    }
}
