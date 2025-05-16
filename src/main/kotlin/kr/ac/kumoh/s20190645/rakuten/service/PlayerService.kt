package kr.ac.kumoh.s20190645.rakuten.service

import kr.ac.kumoh.s20190645.rakuten.model.Player
import kr.ac.kumoh.s20190645.rakuten.repository.PlayerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlayerService(private val playerRepository: PlayerRepository) {
    fun findAllPlayer() = playerRepository.findAll()
    fun findRandom() = playerRepository.findAll().random()
    fun findPlayer(backNumber: Int?): Player? {
        return playerRepository.findByBackNumber(backNumber)
    }


    fun addPlayer(name: String, backNumber: Int?,who:String): String? {
        if (backNumber != null && (backNumber < 0 || backNumber > 150)) return "背番号は０から１５０までです．"
        if (!isOnlyKanji(name)) return "名前は漢字のみ入力してください"
        if (findPlayer(backNumber)!=null)
            return "選手名、または背番号がすでに存在しています"

        playerRepository.save(
            Player(
                null,
                name,
                backNumber,
                "https://www.rakuteneagles.jp/media/sites/3/team/player/2025/${backNumber}.jpg",
                who
            )
        )
        return "$name ( $backNumber ）選手追加できました"
    }

    fun updatePlayer(found: Player, name: String, backNumber: Int?): String {
        if (backNumber == null) return "背番号をちゃんと入力してください"
        if (backNumber < 0 || backNumber > 150) return "背番号は０から１５０までです．"
        if (!isOnlyKanji(name)) return "名前は漢字のみ入力してください"
        found.name = name
        found.backNumber = backNumber
        found.src = "https://www.rakuteneagles.jp/media/sites/3/team/player/2025/${backNumber}.jpg"
        playerRepository.save(found)
        return "$name ( $backNumber ) 選手に更新できました "
    }

    fun findPlayerIndexed(number: Int): Page<Player> {
        val pageable: Pageable = PageRequest.of(number,5)
        return playerRepository.findAllByOrderByBackNumberAsc(pageable)
    }


    @Transactional
    fun deletePlayer(backNumber: Int?) : Int {
        if (findPlayer(backNumber) != null) {
            playerRepository.deleteByBackNumber(backNumber)
            return 1
        } else{
            return 0
        }
    }

    fun isOnlyKanji(text: String): Boolean {
        val kanjiRegex = Regex("[\u4E00-\u9FFF]+$")
        return kanjiRegex.matches(text)
    }
}
