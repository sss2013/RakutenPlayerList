package kr.ac.kumoh.s20190645.rakuten.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PlayerController {
    @GetMapping("/list")
    fun list(model: Model) : String{
        model.addAttribute("name", "Kishi")
        return "list"
    }
}