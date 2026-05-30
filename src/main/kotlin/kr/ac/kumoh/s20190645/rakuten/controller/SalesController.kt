package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.service.SalesService
import kr.ac.kumoh.s20190645.rakuten.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class OrderRequest(
    val productId: Long,
    val quantity: Int,
    val pricePerUnit: Int
)

data class OrderResponse<T>(
    val message : String,
    val data : T? = null
)

@PreAuthorize("hasRole('USER')")
@RestController
class SalesController (
    private val salesService : SalesService,
    private val userService : UserService,
) {

    @PostMapping("/makeNewSale")
    fun makeNewSale(@RequestBody req: OrderRequest,@AuthenticationPrincipal userDetail : UserDetails): ResponseEntity<Map<String,Any>> {
        try {
            val userId = userService.getId(userDetail.username)
            val productId = req.productId
            val quantity = req.quantity
            val price = req.pricePerUnit

            if (userId == null) {
                return ResponseEntity(mapOf("message" to "登録されてないユーザーです"), HttpStatus.UNAUTHORIZED)
            }

            if (quantity <=0 || price<=0) {
                return ResponseEntity(mapOf("message" to "数量か価格が正しくありません"), HttpStatus.BAD_REQUEST)
            }

            salesService.makeNewSale(productId,userId,quantity,price)
            return ResponseEntity(mapOf("message" to "注文に成功しました"), HttpStatus.OK)
        } catch(e: Exception) {
            return ResponseEntity(mapOf("message" to "${e.message}"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}