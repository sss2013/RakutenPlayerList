package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.model.MyUserDetails
import kr.ac.kumoh.s20190645.rakuten.service.PlayerService
import kr.ac.kumoh.s20190645.rakuten.service.S3Service
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class PlayerController(
    private val playerService: PlayerService,
    private val s3Service:S3Service,
) {

    @GetMapping("/")
    fun home(): String {
        return "Normal/index"
    }

    @GetMapping("/list")
    fun list(model: Model): String {
        val result = playerService.findAllPlayer()
        model.addAttribute("players", result)
        return "Normal/list"
    }

    @GetMapping("/list/{curr}")
    fun listByPage(model: Model, @PathVariable curr: Int): String {
        val result = playerService.findPlayerIndexed(curr)

        model.addAttribute("page", curr)
        model.addAttribute("totalPages", result.totalPages)
        model.addAttribute("players", result.content)
        return "Normal/listPage"
    }

    @GetMapping("/random")
    fun random(model: Model): String {
        val result = playerService.findRandom()
        model.addAttribute("player", result)
        return "Normal/random"
    }

    @GetMapping("/playerInfo/{backNumber}")
    fun info(model: Model, @PathVariable backNumber: Int): String {
        val result = playerService.findPlayer(backNumber)
        model.addAttribute("player", result)
        return "Normal/playerInfo"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    fun add(): String {
        return "Operation/add"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/playerAdd")
    fun addPlayer(model: Model, @RequestParam params: Map<String, String>, auth: Authentication): String {
        val name = params["name"] ?: "unknown"
        val backNumber = params["backNumber"]?.toIntOrNull()
        val who = auth.principal as MyUserDetails
        val url = params["url"] ?: "unknown"

        val addResult = playerService.addPlayer(name, backNumber, who.nickname,url)
        model.addAttribute("result", addResult)
        return "Operation/addResult"
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{backNumber}")
    fun updateForm(model: Model, @PathVariable backNumber: Int): String {
        val found = playerService.findPlayer(backNumber)
        model.addAttribute("player", found)
        return "Operation/update"
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
        return "Operation/updateResult"
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    @ResponseBody
    fun deletePlayer(@RequestBody params: Map<String, String>, auth: Authentication): String {
        if (auth.authorities.none { it.authority == "ROLE_ADMIN" }) {
            return "Normal/AccessDenied"
        }
        val number = params["playerNumber"]?.toIntOrNull()
        if (playerService.deletePlayer(number) != 1)
            return "playerInfo/${number}"
        return "/list"
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    fun getURL(@RequestParam filename: String): String{
        val result = s3Service.createPresignedUrl("test/$filename" )
        return result
    }
}
