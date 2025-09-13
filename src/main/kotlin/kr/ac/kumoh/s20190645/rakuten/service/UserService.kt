package kr.ac.kumoh.s20190645.rakuten.service

import kr.ac.kumoh.s20190645.rakuten.model.User
import kr.ac.kumoh.s20190645.rakuten.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, val encoder: BCryptPasswordEncoder) {

    fun addUser(userName: String, passWord: String, nickName: String): String {
        if (!isValidId(userName)) {
            return "IDは英語と数字を含めて５文字以上にしてください"
        }

        if (!isContainsAdmin(userName)) {
            return "IDに不適切な文字が含まれています"
        }

        if (!isValidPassword(passWord)) {
            return "パスワードは英語と数字、記号を含めて８文字以上にしてください"
        }

        val nickNameResult = isValidNickname(nickName)

        if (nickNameResult != "true") {
            return nickNameResult
        }

        if (isDuplicateId(userName))
            return "IDが重複しています"
        if (isDuplicateNickname(nickName))
            return "ニックネームが重複しています"

        val lastNumber = userRepository.findMaxNumber() ?: 0

        userRepository.save(User(null, userName, encoder.encode(passWord), nickName, lastNumber + 1))
        return "ok"
    }

    fun isDuplicateId(userName: String): Boolean {
        val found = userRepository.findByUsername(userName)?.username
        return found != null
    }

    fun isDuplicateNickname(nickName: String): Boolean {
        val found = userRepository.findByNickname(nickName)?.nickname
        return found != null
    }

    fun isValidNickname(nickName: String): String {
        val length = nickName.length > 2
        val pattern = Regex("[\u30A0-\u30FF\u3040-\u309F\u4E00-\u9FFFA-Za-z]")
        val check = pattern.containsMatchIn(nickName)
        return if (!length) "ニックネームは３文字以上にしてください"
        else if (!check) "ニックネームには英語と漢字、ひらがなとカタカナでいずれか１文字以上入れてください"
        else
            "true"
    }

    fun isValidId(id: String): Boolean {
        val regex = Regex("^[a-z0-9]{5,15}$")
        return regex.matches(id)
    }

    fun isContainsAdmin(id: String): Boolean {
        return !id.contains("admin")
    }

    fun isValidPassword(passWord: String): Boolean {
        val length = passWord.length >= 8
        val hasLetter = passWord.contains(Regex("[A-Za-z]"))
        val hasDigit = passWord.contains(Regex("[0-9]"))
        val hasSpecial = passWord.contains(Regex("[^A-Za-z0-9]"))

        return length && hasLetter && hasDigit && hasSpecial
    }

    fun getUser(number: Long?): User? {
        val found = userRepository.findByNumber(number) ?: return null
        return found
    }
}
