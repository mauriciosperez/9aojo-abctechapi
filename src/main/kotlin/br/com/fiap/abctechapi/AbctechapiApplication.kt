package br.com.fiap.abctechapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class AbctechapiApplication

fun main(args: Array<String>) {
    runApplication<AbctechapiApplication>(*args)
}
