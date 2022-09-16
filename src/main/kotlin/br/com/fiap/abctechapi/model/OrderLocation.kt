package br.com.fiap.abctechapi.model

import lombok.Data
import org.springframework.data.annotation.Id
import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@Data
data class OrderLocation(
    @javax.persistence.Id @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val date: Date = Date()
)