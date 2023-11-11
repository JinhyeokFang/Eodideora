package com.example.eodideora.config

import com.example.eodideora.auth.AccessTokenFilter
import com.example.eodideora.auth.SuccessHandler
import com.example.eodideora.util.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    val successHandler: SuccessHandler,
    val jwtProvider: JwtProvider
) {
    @Bean
    fun filterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        return httpSecurity {
            authorizeExchange {
                authorize("/token", permitAll)
                authorize(anyExchange, authenticated)
            }
            addFilterBefore(AccessTokenFilter(jwtProvider), SecurityWebFiltersOrder.AUTHORIZATION)
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
