package kr.ac.kumoh.s20190645.rakuten.controller

import kr.ac.kumoh.s20190645.rakuten.service.ProductService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@Controller
class ProductController(private val productService: ProductService) {

    @GetMapping("/products")
    fun showProducts(model: Model) : String{
        val products = productService.findAll()
        model.addAttribute("products",products)
        return "Normal/productList"
    }

    @GetMapping("/products/{id}")
    fun showProductById(@PathVariable("id") id : Long,model: Model): String{
        val item = productService.findById(id)
        if (item != null) {
            model.addAttribute("product",item)
            return "Normal/productDetail"
        } else {
            return "redirect:/products"
        }
    }

}