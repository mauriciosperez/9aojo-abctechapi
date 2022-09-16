package br.com.fiap.abctechapi.application

import br.com.fiap.abctechapi.application.dto.AssistDto

interface AssistanceApplication {
    fun getAssists(): List<AssistDto>
}