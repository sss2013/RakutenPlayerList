package kr.ac.kumoh.s20190645.rakuten.repository

import kr.ac.kumoh.s20190645.rakuten.model.Player
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PlayerRepository : JpaRepository<Player,Long> {
    fun findByBackNumber(backNumber: Int?) : Player?
    fun deleteByBackNumber(backNumber: Int?) : Int?
    fun findAllByOrderByBackNumberAsc(pageable : Pageable): Page<Player>
}
