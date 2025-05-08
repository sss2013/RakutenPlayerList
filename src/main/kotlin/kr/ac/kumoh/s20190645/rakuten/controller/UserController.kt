package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.model.MyUserDetails
import kr.ac.kumoh.s20190645.rakuten.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
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
        return "/Normal/signIn"
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
        return "/Normal/login"
    }

    @GetMapping("/my-page")
    fun myPage(auth : Authentication,model: Model) :String{
        if (!auth.isAuthenticated)
            return "/Normal/list"

        val userDetails = auth.principal as MyUserDetails
        model.addAttribute("nickName",userDetails.nickname)
        return "/Operation/myPage"
    }

    @GetMapping("/access-denied")
    fun accessDenied() : String {
        return "/Normal/accessDenied"
    }

}