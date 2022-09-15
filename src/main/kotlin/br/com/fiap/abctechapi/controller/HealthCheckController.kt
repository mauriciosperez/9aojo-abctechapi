package br.com.fiap.abctechapi.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/")
class HealthCheckController {
    @GetMapping("version")
    fun status(): ResponseEntity<String> {
        val properties = Properties()
        val inputStream = javaClass.classLoader.getResourceAsStream("application.yml")
        return try {
            properties.load(inputStream)
            ResponseEntity.ok(properties.getProperty("build.name") + " - " + properties.getProperty("build.version"))
        } catch (e: IOException) {
            ResponseEntity.badRequest().build()
        }
    }
}