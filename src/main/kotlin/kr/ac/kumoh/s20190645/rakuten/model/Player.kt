package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Player(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    val name: String,
    val backNumber: Int,
    val src: String,

    ) {
    constructor() : this(0L, "", 0,"") {}
}