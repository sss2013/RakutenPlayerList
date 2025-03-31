package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.repository.PlayerRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import kr.ac.kumoh.s20190645.rakuten.service.PlayerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PlayerController(
    private val playerService: PlayerService,
    private val playerRepository: PlayerRepository
) {

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

    @GetMapping("/add")
    fun add() : String {
        return "add"
    }

    @PostMapping("/playerAdd")
    fun addPlayer(@RequestParam params: Map<String,String>) :String{
        val name = params["name"]?:"unknown"
        val backNumber = params["backNumber"]?.toIntOrNull()
        if (name=="unknown"  || backNumber==null) {
            return "../static/index"
        }
        playerService.addPlayer(name,backNumber)
        return "redirect:/list"
    }
}