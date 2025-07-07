package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.model.MyUserDetails
import kr.ac.kumoh.s20190645.rakuten.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class UserController (
    private val userService : UserService
) {

    /** 회원가입 페이지를 표시하는 기능 (이미 로그인된 경우 목록 페이지로 리다이렉트) */
    @GetMapping("/signIn")
    fun signIn(auth: Authentication?): String {
        if (auth?.isAuthenticated == true)
            return "redirect:Normal/list"

        return "Normal/signIn"
    }

    /** 회원가입 정보를 처리하는 기능 */
    @PostMapping("/infoInput")
    @ResponseBody
    fun signCheck(@RequestParam params:Map<String,String>): String{
        val username = params["username"] ?: return "IDは必須です"
        val password = params["password"] ?: return "パスワードは必須です"
        val nickname = params["nickname"] ?: return "ニックネームは必須です"
        val result= userService.addUser(username,password,nickname)
        return result
    }

    /** 로그인 페이지를 표시하는 기능 */
    @GetMapping("/login")
    fun loginForm() : String{
        return "Normal/login"
    }

    /** 인증된 사용자의 마이페이지를 표시하는 기능 */
    @GetMapping("/my-page")
    fun myPage(auth : Authentication,model: Model) :String{
        if (!auth.isAuthenticated)
            return "Normal/list"

        val userDetails = auth.principal as MyUserDetails
        model.addAttribute("nickName",userDetails.nickname)
        return "Operation/myPage"
    }

    /** 접근 거부 페이지를 표시하는 기능 */
    @GetMapping("/access-denied")
    fun accessDenied() : String {
        return "Normal/accessDenied"
    }

    /** 특정 번호의 사용자 정보를 JSON으로 반환하는 기능 */
    @GetMapping("/user/{number}")
    @ResponseBody
    fun getUser(@PathVariable number:Long?): UserData {
        val user = userService.getUser(number)
        return UserData(user?.username ?: "", user?.nickname ?: "")
    }
}

/** 사용자 정보를 담는 데이터 클래스 */
data class UserData(
    val username: String = "",
    val nickname: String = ""
)
