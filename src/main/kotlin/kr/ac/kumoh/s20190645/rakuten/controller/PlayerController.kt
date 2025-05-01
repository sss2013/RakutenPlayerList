package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.service.PlayerService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
        return "index"
    }

    @GetMapping("/list")
    fun list(model: Model): String {
        val result = playerService.findAllPlayer()
        model.addAttribute("players", result)
        return "list"
    }

    @GetMapping("/random")
    fun random(model: Model): String {
        val result = playerService.findRandom()
        model.addAttribute("player", result)
        return "random"
    }

    @GetMapping("/info/{backNumber}")
    fun info(model: Model, @PathVariable backNumber: Int): Any {
        return try {
            val result = playerService.findPlayer(backNumber)
            model.addAttribute("player", result)
            "info"
        } catch (e: Exception) {
            logger.error("error!", e)
            return ResponseEntity.status(400).body("登録された選手がおりません")
        }
    }

    @GetMapping("/add")
    fun add(): String {
        return "add"
    }

    @PostMapping("/playerAdd")
    fun addPlayer(model: Model, @RequestParam params: Map<String, String>): String {
        val name = params["name"] ?: "unknown"
        val backNumber = params["backNumber"]?.toIntOrNull()
        val addResult = playerService.addPlayer(name, backNumber)
        model.addAttribute("result", addResult)
        return "addResult"
    }

    @GetMapping("/update/{backNumber}")
    fun updateForm(model: Model, @PathVariable backNumber: Int): String {
        val found = playerService.findPlayer(backNumber)
        model.addAttribute("player", found)
        return "update"
    }

    @PostMapping("/playerUpdate")
    fun update(model: Model, @RequestParam params: Map<String, String>): String {
        val info = params["info"]?.toIntOrNull()
        val found = playerService.findPlayer(info)!!
        val name = params["name"] ?: "unknown"
        val backNumber = params["backNumber"]?.toIntOrNull()
        val result = playerService.updatePlayer(found, name, backNumber)
        model.addAttribute("result", result)
        return "updateResult"
    }

    @PostMapping("/delete")
    @ResponseBody
    fun deletePlayer(@RequestBody params: Map<String, String>): String {
        val number = params["playerNumber"]?.toIntOrNull()
        if (playerService.deletePlayer(number) != 1)
            return "/info/${number}"
        return "/list"
    }



}