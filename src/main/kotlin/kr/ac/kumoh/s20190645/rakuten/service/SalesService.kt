package kr.ac.kumoh.s20190645.rakuten.service

import kr.ac.kumoh.s20190645.rakuten.model.Sale
import kr.ac.kumoh.s20190645.rakuten.repository.SalesRepository
import org.springframework.stereotype.Service

@Service
class SalesService(
    private val salesRepository: SalesRepository,
) {
    fun makeNewSale(productId : Long, userId: Long, quantity:Int, price:Int)  {
        salesRepository.save(
            Sale(
                productId = productId,
                quantity = quantity,
                userId = userId,
                pricePerUnit = price,
            )
        )
    }

    fun fetchAllSales(userId: Long) : List<Sale?> {
        return salesRepository.findAllByUserId(userId)
    }

}