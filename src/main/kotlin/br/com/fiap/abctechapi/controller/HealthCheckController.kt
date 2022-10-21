package br.com.fiap.abctechapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController @Autowired constructor(private val buildProperties: BuildProperties) {
    @GetMapping("/version")
    fun status(): ResponseEntity<String> =
        ResponseEntity.ok("${buildProperties.name}-${buildProperties.version}")
}
