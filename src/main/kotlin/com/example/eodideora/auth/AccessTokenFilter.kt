package com.example.eodideora.auth

import com.example.eodideora.util.JwtProvider
import com.example.eodideora.util.JwtType
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Configuration
class AccessTokenFilter(
    private val jwtProvider: JwtProvider
): WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val bearer = exchange.request.headers["Authorization"]
        if (bearer != null && bearer.size > 0) {
            parseToken(bearer[0])
        }
        return chain.filter(exchange)
    }

    private fun parseToken(bearer: String) {
        val token = bearer.substring(7)
        try {
            val claims = jwtProvider.parseJwtToken(token)
            if (claims.subject ==
                JwtType.REFRESH_TOKEN.toString()
            ) {
                throw ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "잘못된 토큰입니다."
                )
            }
            val email = claims["email"]
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(email, "")
        } catch (e: Exception) {
            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "잘못된 토큰입니다."
            )
        }
    }
}
