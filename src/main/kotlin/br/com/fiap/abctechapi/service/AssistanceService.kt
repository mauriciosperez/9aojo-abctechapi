package br.com.fiap.abctechapi.service

import br.com.fiap.abctechapi.model.Assistance

interface AssistanceService {
    fun getAssitanceList(): List<Assistance>
}