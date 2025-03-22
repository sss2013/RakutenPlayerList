package kr.ac.kumoh.s20190645.rakuten.service

import kr.ac.kumoh.s20190645.rakuten.repository.PlayerRepository
import org.springframework.stereotype.Service

@Service
class PlayerService (private val playerRepository : PlayerRepository) {
    fun findAllPlayer() = playerRepository.findAll()
    fun findRandom() = playerRepository.findAll().random()
}