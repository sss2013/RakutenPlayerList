package kr.ac.kumoh.s20190645.rakuten.repository

import kr.ac.kumoh.s20190645.rakuten.model.Player
import org.springframework.data.jpa.repository.JpaRepository

interface PlayerRepository : JpaRepository<Player,Long> {

}