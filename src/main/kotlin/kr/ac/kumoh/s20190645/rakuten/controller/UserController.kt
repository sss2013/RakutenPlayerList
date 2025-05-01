package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class UserController (
    private val userService : UserService
) {

    @GetMapping("/signIn")
    fun signIn() : String{
        return "signIn"
    }

    @PostMapping("/infoInput")
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
        return "login"
    }
}