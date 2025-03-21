package kr.ac.kumoh.s20190645.rakuten.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import kr.ac.kumoh.s20190645.rakuten.service.PlayerService

@Controller
class PlayerController (private val playerService : PlayerService ) {

    @GetMapping("/list")
    fun list(model: Model) : String{
        val result= playerService.findAllPlayer()
        println(result[0])
        model.addAttribute("name", "Kishi")
        return "list"
    }
}