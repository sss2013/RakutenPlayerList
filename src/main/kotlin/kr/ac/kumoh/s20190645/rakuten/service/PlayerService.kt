package kr.ac.kumoh.s20190645.rakuten.service

import kr.ac.kumoh.s20190645.rakuten.repository.PlayerRepository
import org.springframework.stereotype.Service
import kr.ac.kumoh.s20190645.rakuten.model.Player

@Service
class PlayerService (private val playerRepository : PlayerRepository) {
    fun findAllPlayer() = playerRepository.findAll()
    fun findRandom() = playerRepository.findAll().random()

    fun addPlayer(name:String,backNumber:Int?){
        playerRepository.save(Player(null,name,backNumber,"https://www.rakuteneagles.jp/media/sites/3/team/player/2025/${backNumber}.jpg"))
    }

    fun findPlayer(backNumber:Int) : Player?{
        return playerRepository.findByBackNumber(backNumber)
    }
}