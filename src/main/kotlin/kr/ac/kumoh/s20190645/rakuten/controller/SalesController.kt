package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.service.SalesService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

data class OrderRequest(
    val productId: Long,
    val quantity: Int,
    val pricePerUnit: Int? = null // 클라이언트 값은 신뢰하지 말고 서버에서 재검증
)

@RestController
class SalesController (
    private val salesService : SalesService,
) {
    @PostMapping("/orderProduct")
    fun orderProduct(@RequestBody req: OrderRequest): ResponseEntity<Any> {
        print("${req.productId} ${req.quantity} ${req.pricePerUnit ?: 0}")
        return ResponseEntity.ok(mapOf("result" to "ok"))
    }
}