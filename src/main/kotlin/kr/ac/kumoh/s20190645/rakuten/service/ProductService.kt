package kr.ac.kumoh.s20190645.rakuten.service

import kr.ac.kumoh.s20190645.rakuten.model.Product
import kr.ac.kumoh.s20190645.rakuten.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService (private val productRepository: ProductRepository) {
    fun findAll(): List<Product> = productRepository.findAll()
    fun findById(id : Long) : Product? = productRepository.findByIdOrNull(id)
}