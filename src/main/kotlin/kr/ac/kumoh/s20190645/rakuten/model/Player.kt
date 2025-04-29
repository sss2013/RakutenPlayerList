package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Player(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var name: String?,
    var backNumber: Int?,
    var src: String,
    ) {
    constructor() : this(null, "", 0,"") {}
}