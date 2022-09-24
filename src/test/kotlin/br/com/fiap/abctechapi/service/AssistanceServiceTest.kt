package br.com.fiap.abctechapi.service

import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.repository.AssistanceRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class AssistanceServiceTest {
    @MockK
    lateinit var assistanceRepository: AssistanceRepository
    lateinit var assistanceService: AssistanceService

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        assistanceService = AssistanceService(assistanceRepository)
    }

    @Test
    @DisplayName("LIstando assistencias disponiveis :: Sucesso")
    fun list_success() {
        val assistances = listOf(
            Assistance(id = 1L, name = "Mock 1", description = "Description 1"),
            Assistance(id = 2L, name = "Mock 2", description = "Description 2"),
            Assistance(id = 3L, name = "Mock 3", description = "Description 3"),
        )

        every { assistanceRepository.findAll() } returns assistances

        val values = assistanceService.getAssitanceList()
        assertEquals(3, values.size)
        assertEquals(values[0], Assistance(1L, "Mock 1", "Description 1"))
        assertEquals(values[1], Assistance(2L, "Mock 2", "Description 2"))
    }

    @Test
    @DisplayName("Listando assistencias disponiveis :: Falha")
    fun list_failure() {
        every { assistanceRepository.findAll() } returns listOf()

        val values = assistanceService.getAssitanceList()
        assertEquals(0, values.size)
    }
}
