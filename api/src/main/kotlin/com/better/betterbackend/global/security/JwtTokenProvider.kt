package com.better.betterbackend.global.security

import com.better.betterbackend.domain.user.service.UserDetailService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.security.Key
import java.security.KeyPair
import java.security.KeyStore.PrivateKeyEntry
import java.util.*

@Service
@PropertySource("classpath:jwt.yml")
class JwtTokenProvider (

    @Value("\${header}")
    private val header: String,

    @Value("\${secret}")
    private val secretKey: String,

    @Value("\${token-validity-in-milliseconds}")
    private val tokenValidTime: Long,

    private val userDetailService: UserDetailService,

){

    fun createToken(userPK: String): String {
        val claims: Claims = Jwts.claims().setSubject(userPK)
        claims["userPK"] = userPK
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("type", "JWT")
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidTime))
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)), SignatureAlgorithm.HS256)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUserPk(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey.toByteArray(Charsets.UTF_8)).build().parseClaimsJws(token).body.subject
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(header)
    }

    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(secretKey.toByteArray(Charsets.UTF_8)).build().parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

}