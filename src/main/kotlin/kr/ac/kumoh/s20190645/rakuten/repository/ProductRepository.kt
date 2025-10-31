package kr.ac.kumoh.s20190645.rakuten.repository

import kr.ac.kumoh.s20190645.rakuten.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

}