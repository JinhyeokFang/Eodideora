package com.example.eodideora.auth

import com.example.eodideora.util.JwtProvider
import com.example.eodideora.util.JwtType
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.net.URI

@Component
class SuccessHandler(
    @Value("\${spring.security.cors.client-origin}")
    private val clientOrigin: String? = null,
    private val jwtProvider: JwtProvider
): ServerAuthenticationSuccessHandler {
    private val logger: Logger = LoggerFactory.getLogger("SuccessHandler")

    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange?,
        authentication: Authentication?
    ): Mono<Void> {
        val principal: OAuth2User = authentication!!.principal as OAuth2User
        val attributes = principal.attributes
        val token = jwtProvider.createToken(JwtType.REFRESH_TOKEN, attributes["email"] as String)
        val response = webFilterExchange!!.exchange.response
        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT)
        response.headers.location = URI.create(clientOrigin as String)
        runBlocking {
            webFilterExchange.exchange.session.subscribe {
                session -> session.attributes.set("refresh-token", token)
            }
        }
        return response.setComplete()
    }
}
