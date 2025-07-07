package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.model.MyUserDetails
import kr.ac.kumoh.s20190645.rakuten.service.PlayerService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class PlayerController(
    private val playerService: PlayerService,
) {

    /** 홈페이지를 표시하는 기능 */
    @GetMapping("/")
    fun home(): String {
        return "Normal/index"
    }

    /** 모든 선수 목록을 표시하는 기능 */
    @GetMapping("/list")
    fun list(model: Model): String {
        val result = playerService.findAllPlayer()
        model.addAttribute("players", result)
        return "Normal/list"
    }

    /** 페이지네이션을 사용하여 선수 목록을 표시하는 기능 */
    @GetMapping("/list/{curr}")
    fun listByPage(model:Model, @PathVariable curr:Int) : String{
        val result=playerService.findPlayerIndexed(curr)

        model.addAttribute("page",curr)
        model.addAttribute("totalPages",result.totalPages)
        model.addAttribute("players", result.content)
        return "Normal/listPage"
    }

    /** 무작위 선수를 표시하는 기능 */
    @GetMapping("/random")
    fun random(model: Model): String {
        val result = playerService.findRandom()
        model.addAttribute("player", result)
        return "Normal/random"
    }

    /** 특정 배번호의 선수 상세 정보를 표시하는 기능 */
    @GetMapping("/info/{backNumber}")
    fun info(model: Model, @PathVariable backNumber: Int): String {
        val result = playerService.findPlayer(backNumber)
        model.addAttribute("player", result)
        return "Normal/info"
    }

    /** 관리자 전용: 선수 추가 폼을 표시하는 기능 */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    fun add(): String {
        return "Operation/add"
    }

    /** 관리자 전용: 새로운 선수를 추가하는 기능 */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/playerAdd")
    fun addPlayer(model: Model, @RequestParam params: Map<String, String>, auth: Authentication): String {
        val name = params["name"] ?: "unknown"
        val backNumber = params["backNumber"]?.toIntOrNull()
        val who = auth.principal as MyUserDetails
        val addResult = playerService.addPlayer(name, backNumber,who.nickname)
        model.addAttribute("result", addResult)
        return "Operation/addResult"
    }

    /** 관리자 전용: 선수 정보 수정 폼을 표시하는 기능 */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{backNumber}")
    fun updateForm(model: Model, @PathVariable backNumber: Int): String {
        val found = playerService.findPlayer(backNumber)
        model.addAttribute("player", found)
        return "Operation/update"
    }

    /** 관리자 전용: 선수 정보를 수정하는 기능 */
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

    /** 관리자 전용: 선수를 삭제하는 기능 */
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
