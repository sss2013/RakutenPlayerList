package kr.ac.kumoh.s20190645.rakuten

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession

@SpringBootApplication
@EnableJdbcHttpSession
class RakutenApplication

fun main(args: Array<String>) {
    runApplication<RakutenApplication>(*args)
}