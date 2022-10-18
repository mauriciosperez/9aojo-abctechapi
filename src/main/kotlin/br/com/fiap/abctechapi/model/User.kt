package br.com.fiap.abctechapi.model

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["username"]),
        UniqueConstraint(columnNames = ["email"])
    ]
)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,
    @Size(max = 20)
    @Column
    var username: String = "",
    @Email
    @Size(max = 50)
    @Column(name = "email", nullable = false)
    var email: String = "",
    @Size(max = 120)
    @Column(name = "password", nullable = false)
    var password: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String =
        this::class.simpleName + "(id = $id , username = $username , email = $email , password = $password )"
}
