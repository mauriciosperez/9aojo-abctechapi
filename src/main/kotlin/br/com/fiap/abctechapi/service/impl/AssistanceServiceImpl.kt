package br.com.fiap.abctechapi.service.impl

import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.repository.AssistanceRepository
import br.com.fiap.abctechapi.service.AssistanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AssistanceServiceImpl @Autowired constructor(private val repository: AssistanceRepository) : AssistanceService {
    override fun getAssitanceList(): List<Assistance> = repository.findAll()
}
