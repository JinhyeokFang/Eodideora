package com.example.eodideora.auth

import com.example.eodideora.common.jwt.JwtProvider
import com.example.eodideora.common.jwt.JwtType
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
class AccessTokenFilter(
    private val jwtProvider: JwtProvider,
): WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val bearer = exchange.request.headers["Authorization"]
        if (bearer != null && bearer.size > 0) {
            val authorities = mutableListOf<SimpleGrantedAuthority>()
            authorities.add(SimpleGrantedAuthority("ROLE_USER"))
            val authentication: Authentication = UsernamePasswordAuthenticationToken("id", null, authorities);
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
            parseToken(bearer[0])
        }
        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext())
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
