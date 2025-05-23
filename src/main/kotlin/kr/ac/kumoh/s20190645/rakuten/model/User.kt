package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.*

@Entity
@Table(name= "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var username: String,
    var password: String,
    @Column(columnDefinition = "NVARCHAR(100)")
    var nickname: String,
    var number: Long?
) {
    constructor() : this(null, "", "","",null)
}
