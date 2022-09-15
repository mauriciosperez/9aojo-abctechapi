package br.com.fiap.abctechapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
open class AbctechapiApplication

fun main(args: Array<String>) {
    SpringApplication.run(AbctechapiApplication::class.java, *args)
}
