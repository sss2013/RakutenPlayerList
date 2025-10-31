package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,
    var name: String= "",
    var price: Int = 0,
    @Column(name = "image", columnDefinition = "NVARCHAR(1000)")
    var image: String ="",
    var category: String="",
)