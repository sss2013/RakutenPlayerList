package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name= "sales")
data class Sale(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long=0,
    @Column(name="user_id", nullable=false)
    val userId: Long,
    @Column(name="product_id", nullable=false)
    val productId : Long,
    @Column(nullable=false)
    val quantity : Int,
    //注文した時点の価格
    @Column(name="price_per_unit", nullable=false)
    val pricePerUnit : Int,
    @Column(name="created_at",nullable=false)
    val createdAt : OffsetDateTime = OffsetDateTime.now(),
)
