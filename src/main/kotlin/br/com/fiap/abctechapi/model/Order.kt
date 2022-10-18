package br.com.fiap.abctechapi.model

import org.hibernate.Hibernate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,
    @Column(name = "operator_id", nullable = false)
    var operatorId: Long = 0,
    @ManyToMany
    var assists: List<Assistance> = listOf(),
    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "start_order_location_id", foreignKey = ForeignKey(name = "FK_start_order_id"))
    var startOrderLocation: OrderLocation = OrderLocation(),
    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "end_order_location_id", foreignKey = ForeignKey(name = "FK_end_order_id"))
    var endOrderLocation: OrderLocation = OrderLocation()
) {
    fun hasMinAssists() = assists.isNotEmpty()
    fun exceedsMaxAssists() = assists.size > 15
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Order

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , operatorId = $operatorId , startOrderLocation = $startOrderLocation , endOrderLocation = $endOrderLocation )"
    }
}
