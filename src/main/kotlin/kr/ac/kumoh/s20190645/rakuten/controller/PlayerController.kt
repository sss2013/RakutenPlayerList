package kr.ac.kumoh.s20190645.rakuten.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import kr.ac.kumoh.s20190645.rakuten.service.PlayerService

@Controller
class PlayerController (private val playerService : PlayerService ) {

    @GetMapping("/list")
    fun list(model: Model) : String {
        val result = playerService.findAllPlayer()
        model.addAttribute("players", result)
        return "list"
    }

    @GetMapping("/random")
    fun random(model: Model) : String {
        val result = playerService.findRandom()
        model.addAttribute("player",result)
        return "random"
    }

}