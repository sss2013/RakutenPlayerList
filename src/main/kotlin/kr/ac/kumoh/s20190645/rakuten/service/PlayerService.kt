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
    
    /** 모든 선수 정보를 조회하는 기능 */
    fun findAllPlayer() = playerRepository.findAll()
    
    /** 등록된 선수 중 무작위로 한 명을 선택하는 기능 */
    fun findRandom() = playerRepository.findAll().random()
    
    /** 배번호로 특정 선수를 찾는 기능 */
    fun findPlayer(backNumber: Int?): Player? {
        return playerRepository.findByBackNumber(backNumber)
    }


    /**
     * 새로운 선수를 추가하는 기능
     * @param name 선수 이름 (한자만 허용)
     * @param backNumber 배번호 (0-150 범위)
     * @param who 등록자 닉네임
     * @return 추가 결과 메시지
     */
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

    /**
     * 기존 선수 정보를 수정하는 기능
     * @param found 수정할 선수 객체
     * @param name 새로운 이름 (한자만 허용)
     * @param backNumber 새로운 배번호
     * @return 수정 결과 메시지
     */
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

    /** 페이지네이션을 사용하여 선수 목록을 조회하는 기능 (한 페이지당 5명) */
    fun findPlayerIndexed(number: Int): Page<Player> {
        val pageable: Pageable = PageRequest.of(number,5)
        return playerRepository.findAllByOrderByBackNumberAsc(pageable)
    }


    /**
     * 선수를 삭제하는 기능
     * @param backNumber 삭제할 선수의 배번호
     * @return 1: 삭제 성공, 0: 삭제 실패 (선수 없음)
     */
    @Transactional
    fun deletePlayer(backNumber: Int?) : Int {
        if (findPlayer(backNumber) != null) {
            playerRepository.deleteByBackNumber(backNumber)
            return 1
        } else{
            return 0
        }
    }

    /** 문자열이 한자로만 구성되어 있는지 검증하는 기능 */
    fun isOnlyKanji(text: String): Boolean {
        val kanjiRegex = Regex("[\u4E00-\u9FFF]+$")
        return kanjiRegex.matches(text)
    }
}
