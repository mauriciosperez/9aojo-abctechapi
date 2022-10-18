package br.com.fiap.abctechapi.model

import lombok.Data
import org.hibernate.Hibernate
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as OrderLocation

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , latitude = $latitude , longitude = $longitude , date = $date )"
    }
}
