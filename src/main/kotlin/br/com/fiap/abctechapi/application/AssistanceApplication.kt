package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.AssistDto
import br.com.fiap.abctechapi.service.AssistanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AssistanceApplication @Autowired constructor(private val service: AssistanceService) {
    fun getAssists(): MutableList<AssistDto> =
        service.getAssitanceList().map {
            AssistDto(
                id = it.id,
                name = it.name,
                description = it.description
            )
        }.toMutableList()
}
