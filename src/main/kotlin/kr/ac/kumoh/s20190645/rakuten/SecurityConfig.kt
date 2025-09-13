package kr.ac.kumoh.s20190645.rakuten

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.access.AccessDeniedHandlerImpl
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.
            csrf {
                it.csrfTokenRepository(csrfTokenRepository())
                    .ignoringRequestMatchers("/login","/search")
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/Operation/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/my-page")
                    .authenticated()
                    .anyRequest().permitAll()
            }
            .formLogin {
                it
                    .loginPage("/login")
                    .defaultSuccessUrl("/list")
                    .permitAll()
            }
            .logout { it.logoutUrl("/logout") }
            .exceptionHandling { exceptions ->
                exceptions.accessDeniedHandler(customDeniedHandler())
            }
        return http.build()
    }

    @Bean
    fun customDeniedHandler(): AccessDeniedHandler {
        val handler = AccessDeniedHandlerImpl()
        handler.setErrorPage("/access-denied")
        return handler
    }

    @Bean
    fun csrfTokenRepository(): CsrfTokenRepository {
        val repository = HttpSessionCsrfTokenRepository()
        repository.setHeaderName("X-XSRF-TOKEN")
        return repository
    }

}
