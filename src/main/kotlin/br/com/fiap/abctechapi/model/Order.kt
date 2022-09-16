package br.com.fiap.abctechapi.model

import org.springframework.data.annotation.Id
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "orders")
data class Order(
    @javax.persistence.Id @Id
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
}