package com.example.eodideora.auth

import com.example.eodideora.util.JwtProvider
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
class AccessTokenFilter(
    private val jwtProvider: JwtProvider
): WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authorizationHeader = exchange.request.headers["Authorization"]
        LoggerFactory.getLogger("!!!!!!!!!").info(authorizationHeader.toString())
        return chain.filter(exchange)
    }
}
