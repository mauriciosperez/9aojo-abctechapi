package br.com.fiap.abctechapi.controller

import br.com.fiap.abctechapi.model.Assistance
import br.com.fiap.abctechapi.service.AssistanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/assistance")
class AssistanceController @Autowired constructor(private val service: AssistanceService) {
    @GetMapping
    fun getAssists(): ResponseEntity<List<Assistance>> {
        val list = service.getAssitanceList()
        return ResponseEntity.ok(list)
    }
}
