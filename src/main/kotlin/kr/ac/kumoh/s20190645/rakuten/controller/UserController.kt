package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.model.MyUserDetails
import kr.ac.kumoh.s20190645.rakuten.service.SalesService
import kr.ac.kumoh.s20190645.rakuten.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class UserController(
    private val userService: UserService,
    private val salesService: SalesService
) {

    @GetMapping("/signUp")
    fun signUp(@AuthenticationPrincipal auth: MyUserDetails?): String {
        if (auth != null)
            return "redirect:/list"

        return "Normal/signUp"
    }

    @PostMapping("/signUpPost")
    @ResponseBody
    fun signCheck(@RequestParam params: Map<String, String>): String {
        val username = params["username"] ?: return "IDは必須です"
        val password = params["password"] ?: return "パスワードは必須です"
        val nickname = params["nickname"] ?: return "ニックネームは必須です"
        val result = userService.addUser(username, password, nickname)
        return result
    }

    @GetMapping("/login")
    fun loginForm(): String {
        return "Normal/login"
    }

    @GetMapping("/my-page")
    fun myPage(@AuthenticationPrincipal auth: MyUserDetails?, model: Model): String {
        if (auth == null)
            return "redirect:/list"

        val userId = userService.getId(auth.username)

        val salesList = if (userId != null) {
            salesService.fetchAllSales(userId)
        } else {
            emptyList()
        }

        model.addAttribute("nickName", auth.nickname)
        return "Operation/MyPage"
    }

    @GetMapping("/access-denied")
    fun accessDenied(): String {
        return "Normal/AccessDenied"
    }

    @GetMapping("/logout-success")
    fun logoutSuccessUrl(): String {
        return "Normal/index"
    }

    @GetMapping("/user/{number}")
    @ResponseBody
    fun getUser(@PathVariable number: Long?): UserData {
        val user = userService.getUser(number)
        return UserData(user?.username ?: "", user?.nickname ?: "")
    }

    @GetMapping("/check-login")
    @ResponseBody
    fun checkLogin(authentication: Authentication?): ResponseEntity<Map<String, Any>> {
        return if (authentication == null || !authentication.isAuthenticated) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("status" to false))
        } else {
            val username = authentication.name
            ResponseEntity.ok(mapOf("status" to true, "username" to username))
        }
    }

    data class UserData(
        val username: String = "",
        val nickname: String = ""
    )
}
