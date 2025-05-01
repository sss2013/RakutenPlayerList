package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.*

@Entity
@Table(name= "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var username: String?,
    var password: String?,
    var nickname: String?
) {
    constructor() : this(null, "", "","")
}