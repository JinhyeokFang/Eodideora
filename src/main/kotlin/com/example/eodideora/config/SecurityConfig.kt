package com.example.eodideora.config

import com.example.eodideora.auth.SuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    val successHandler: SuccessHandler
) {
    @Bean
    fun filterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        return httpSecurity {
            authorizeExchange {
                authorize("/login", permitAll)
                authorize(anyExchange, authenticated)
            }
            csrf {
                disable()
            }
            cors {
                disable()
            }
            oauth2Login {
                authenticationSuccessHandler = successHandler
            }
        }
    }
}
