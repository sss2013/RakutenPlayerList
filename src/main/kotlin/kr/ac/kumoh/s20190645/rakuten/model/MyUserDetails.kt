package kr.ac.kumoh.s20190645.rakuten.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MyUserDetails (
    private val username:String,
    private val password:String,
    val nickname: String,
    private val authorities: List<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities() = authorities
    override fun getUsername() = username
    override fun getPassword() = password

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}