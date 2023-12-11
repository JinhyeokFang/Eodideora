package com.example.eodideora.user.interfaces

import com.example.eodideora.util.JwtProvider
import com.example.eodideora.util.JwtType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.WebSession

@RestController
class UserController(
    private val jwtProvider: JwtProvider,
) {
    @GetMapping("/token")
    suspend fun getToken(webSession: WebSession): String {
        val token: String = webSession.getAttribute("refresh-token")
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "로그인이 필요합니다."
            )
        val claims = jwtProvider.parseJwtToken(token)
        val accessToken = jwtProvider.createToken(JwtType.ACCESS_TOKEN, claims.get("email") as String)
        return accessToken
    }

    @GetMapping("/test")
    suspend fun test(): String {
        return "accessToken"
    }

    @PostMapping("/test")
    suspend fun test2(): String {
        return "accessToken"
    }
}
