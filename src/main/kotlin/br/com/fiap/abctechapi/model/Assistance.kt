package br.com.fiap.abctechapi.model

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Data
@Table(name = "assistances")
data class Assistance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name", nullable = false, length = 100)
    val name: String = "",
    @Column(name = "description", nullable = false, length = 300)
    val description: String = ""
)
