package br.com.fiap.abctechapi.controller

import br.com.fiap.abctechapi.application.AssistanceApplication
import br.com.fiap.abctechapi.application.dto.AssistDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/assistance")
class AssistanceController @Autowired constructor(private val application: AssistanceApplication) {
    @GetMapping
    fun getAssists(): ResponseEntity<List<AssistDto>> {
        val list = application.getAssists()
        return ResponseEntity.ok(list)
    }
}
