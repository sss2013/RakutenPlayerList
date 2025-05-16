package kr.ac.kumoh.s20190645.rakuten.repository

import kr.ac.kumoh.s20190645.rakuten.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByNickname(nickname: String): User?
    fun findByNumber(number: Long?): User?

    @Query("SELECT MAX(u.number) FROM User u") fun findMaxNumber(): Long?
}
