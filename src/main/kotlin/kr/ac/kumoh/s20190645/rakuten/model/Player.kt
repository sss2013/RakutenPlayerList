package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.*

@Entity
class Player(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var name: String?,
    var backNumber: Int?,
    var src: String,
    @Column(columnDefinition = "NVARCHAR(100)")
    var who: String
    ) {
    constructor() : this(null, "", 0,"","") {}
}
