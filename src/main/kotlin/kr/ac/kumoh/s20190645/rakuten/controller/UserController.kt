package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.model.MyUserDetails
import kr.ac.kumoh.s20190645.rakuten.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class UserController (
    private val userService : UserService
) {

    @GetMapping("/signUp")
    fun signUp(@AuthenticationPrincipal auth: MyUserDetails?): String {
        if (auth != null)
            return "redirect:/list"

        return "Normal/SignUp"
    }

    @PostMapping("/signUpPost")
    @ResponseBody
    fun signCheck(@RequestParam params:Map<String,String>): String{
        val username = params["username"] ?: return "IDは必須です"
        val password = params["password"] ?: return "パスワードは必須です"
        val nickname = params["nickname"] ?: return "ニックネームは必須です"
        val result= userService.addUser(username,password,nickname)
        return result
    }

    @GetMapping("/login")
    fun loginForm() : String{
        return "Normal/login"
    }

    @GetMapping("/my-page")
    fun myPage(@AuthenticationPrincipal auth : MyUserDetails?, model: Model) :String{
        if (auth == null)
            return "redirect:/list"

        model.addAttribute("nickName", auth.nickname)
        return "Operation/MyPage"
    }

    @GetMapping("/access-denied")
    fun accessDenied() : String {
        return "Normal/AccessDenied"
    }

    @GetMapping("/user/{number}")
    @ResponseBody
    fun getUser(@PathVariable number:Long?): UserData {
        val user = userService.getUser(number)
        return UserData(user?.username ?: "", user?.nickname ?: "")
    }

    @GetMapping("/check-login")
    @ResponseBody
    fun checkLogin(@AuthenticationPrincipal user: MyUserDetails?) : ResponseEntity<Map<String, Any>> {
        return if (user == null){
            ResponseEntity.ok(mapOf("loggedIn" to false))
        } else {
            ResponseEntity.ok(mapOf("loggedIn" to true,"username" to user.username))
        }
    }

    data class UserData(
        val username: String = "",
        val nickname: String = ""
    )
}
