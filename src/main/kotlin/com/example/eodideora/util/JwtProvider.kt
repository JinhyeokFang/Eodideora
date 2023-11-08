package com.example.eodideora.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtProvider {
    @Value("\${spring.security.jwt.secret}")
    private var secretKey: String? = null
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun createToken(jwtType: JwtType, email: String): String {
        val now = Date()
        val claims = Jwts.claims()
            .setSubject(jwtType.toString())
            .setIssuedAt(now)
            .setExpiration(Date(now.getTime() + 24 * 60 * 60 * 1000))
        claims["email"] = email
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }

    @Throws(Exception::class)
    fun parseJwtToken(token: String?): Claims {
        return try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw Exception("잘못된 토큰입니다.")
        }
    }
}