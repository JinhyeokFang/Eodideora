package com.example.eodideora.common.config

import com.example.eodideora.auth.AccessTokenFilter
import com.example.eodideora.auth.SuccessHandler
import com.example.eodideora.common.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint

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
                authorize("/oauth2/**", permitAll)
                authorize(anyExchange, authenticated)
            }
            formLogin {
                disable()
            }
            httpBasic {
                disable()
            }
            csrf {
                disable()
            }
            cors {
                disable()
            }
            exceptionHandling {
                authenticationEntryPoint = ServerAuthenticationEntryPoint { exchange, _ ->
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    exchange.response.setComplete()
                }
            }
            addFilterBefore(AccessTokenFilter(jwtProvider), SecurityWebFiltersOrder.AUTHENTICATION)
            oauth2Login {
                authenticationSuccessHandler = successHandler
            }
        }
    }
}
