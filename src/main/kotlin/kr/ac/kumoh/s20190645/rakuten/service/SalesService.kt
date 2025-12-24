package kr.ac.kumoh.s20190645.rakuten.service

import kr.ac.kumoh.s20190645.rakuten.repository.SalesRepository
import org.springframework.stereotype.Service

@Service
class SalesService(
    private val salesRepository: SalesRepository,
) {

}