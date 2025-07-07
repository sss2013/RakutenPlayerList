package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.*

/**
 * 사용자 정보를 저장하는 엔티티 클래스
 * 회원가입, 로그인, 권한 관리를 위한 사용자 데이터를 관리
 */
@Entity
@Table(name= "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var username: String,              // 사용자 ID (영어+숫자, 5-15자)
    var password: String,              // 암호화된 비밀번호
    @Column(columnDefinition = "NVARCHAR(100)")
    var nickname: String,              // 닉네임 (한글/영어, 3자 이상)
    var number: Long?                  // 사용자 고유 번호
) {
    constructor() : this(null, "", "","",null)
}
