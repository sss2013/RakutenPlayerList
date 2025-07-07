package kr.ac.kumoh.s20190645.rakuten.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * Spring Security의 UserDetails를 구현한 사용자 인증 정보 클래스
 * 로그인한 사용자의 인증 및 권한 정보를 관리
 */
class MyUserDetails (
    private val username:String,       // 사용자 ID
    private val password:String,       // 암호화된 비밀번호
    val nickname: String,              // 사용자 닉네임
    private val authorities: List<GrantedAuthority>  // 사용자 권한 목록
) : UserDetails {
    override fun getAuthorities() = authorities
    override fun getUsername() = username
    override fun getPassword() = password

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}