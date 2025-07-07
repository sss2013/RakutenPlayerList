package kr.ac.kumoh.s20190645.rakuten.model

import jakarta.persistence.*

/**
 * 선수 정보를 저장하는 엔티티 클래스
 * 楽天イーグルス 선수들의 이름, 배번호, 사진 URL, 등록자 정보를 관리
 */
@Entity
data class Player(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var name: String?,                 // 선수 이름 (한자만 허용)
    var backNumber: Int?,              // 배번호 (0-150)
    var src: String,                   // 선수 사진 URL
    @Column(columnDefinition = "NVARCHAR(100)")
    var who: String                    // 등록자 닉네임
    ) {
    constructor() : this(null, "", 0,"","") {}
}