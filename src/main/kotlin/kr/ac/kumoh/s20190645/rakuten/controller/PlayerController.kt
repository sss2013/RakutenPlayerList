package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.service.PlayerService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

private val logger = LoggerFactory.getLogger(PlayerController::class.java)

@Controller
class PlayerController(
    private val playerService: PlayerService,
) {

    @GetMapping("/")
    fun home(): String {
        return "/Normal/index"
    }

    @GetMapping("/list")
    fun list(model: Model): String {
        val result = playerService.findAllPlayer()
        model.addAttribute("players", result)
        return "/Normal/list"
    }

    @GetMapping("/random")
    fun random(model: Model): String {
        val result = playerService.findRandom()
        model.addAttribute("player", result)
        return "/Normal/random"
    }

    @GetMapping("/info/{backNumber}")
    fun info(model: Model, @PathVariable backNumber: Int): String {
        val result = playerService.findPlayer(backNumber)
        model.addAttribute("player", result)
        return "/Normal/info"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    fun add(): String {
        return "/Operation/add"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/playerAdd")
    fun addPlayer(model: Model, @RequestParam params: Map<String, String>): String {
        val name = params["name"] ?: "unknown"
        val backNumber = params["backNumber"]?.toIntOrNull()
        val addResult = playerService.addPlayer(name, backNumber)
        model.addAttribute("result", addResult)
        return "/Operation/addResult"
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{backNumber}")
    fun updateForm(model: Model, @PathVariable backNumber: Int): String {
        val found = playerService.findPlayer(backNumber)
        model.addAttribute("player", found)
        return "/Operation/update"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/playerUpdate")
    fun update(model: Model, @RequestParam params: Map<String, String>): String {
        val info = params["info"]?.toIntOrNull()
        val found = playerService.findPlayer(info)!!
        val name = params["name"] ?: "unknown"
        val backNumber = params["backNumber"]?.toIntOrNull()
        val result = playerService.updatePlayer(found, name, backNumber)
        model.addAttribute("result", result)
        return "/Operation/updateResult"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    @ResponseBody
    fun deletePlayer(@RequestBody params: Map<String, String>,auth: Authentication): String {
        if (auth.authorities.none { it.authority == "ROLE_ADMIN" }) {
            return "/access-denied"
        }
        val number = params["playerNumber"]?.toIntOrNull()
        if (playerService.deletePlayer(number) != 1)
            return "/info/${number}"
        return "/list"
    }
}