package kr.ac.kumoh.s20190645.rakuten.repository

import kr.ac.kumoh.s20190645.rakuten.model.Sale
import org.springframework.data.jpa.repository.JpaRepository

interface SalesRepository : JpaRepository<Sale, Long> {
    fun findAllByUserId(userId: Long): List<Sale>
}