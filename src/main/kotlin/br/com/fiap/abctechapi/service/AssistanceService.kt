package br.com.fiap.abctechapi.service

import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.repository.AssistanceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AssistanceService @Autowired constructor(private val repository: AssistanceRepository) {
    fun getAssitanceList(): List<Assistance> = repository.findAll()
}
