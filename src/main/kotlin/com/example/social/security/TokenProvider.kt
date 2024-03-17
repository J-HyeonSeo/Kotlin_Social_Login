package com.example.social.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import io.micrometer.common.util.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.stream.Collectors
import javax.crypto.SecretKey


@Component
class TokenProvider {
    @Value("\${spring.jwt.secret}")
    lateinit var secretKey: String
    val memberIdHeader = "memberId"
    val roleHeader = "roles"
    val accessTokenExpiredTime = 1000 * 60 * 60 * 24 * 14 //1초 -> 1분 -> 1시간 -> 1일 -> 2주

    // Access 토큰 생성
    fun generateJwtToken(memberId: Long, roles: List<String>): String {
        val claims = HashMap<String, Any>()
        claims[this.memberIdHeader] = memberId
        claims[this.roleHeader] = roles

        val nowDate = Date()
        val expiredAt = Date(nowDate.time + this.accessTokenExpiredTime)

        return Jwts.builder()
            .claims(claims)
            .issuedAt(nowDate)
            .expiration(expiredAt)
            .signWith(getSigningKey())
            .compact()
    }

    internal fun getSigningKey(): SecretKey {
        val keyBytes: ByteArray = secretKey.toByteArray(StandardCharsets.UTF_8)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun getAuthentication(token: String): Authentication {

        val memberId = getMemberId(token)
        val roles = getRoles(token)

        val grantedAuthorities = roles.stream()
            .map { SimpleGrantedAuthority(it) }
            .collect(Collectors.toList())

        return UsernamePasswordAuthenticationToken(memberId, "", grantedAuthorities)
    }

    fun getRoles(token: String): List<String> {
        val claims = parseClaims(token)
        val roles = claims.get(this.roleHeader, List::class.java)
        return roles.stream().map { it.toString() }
            .collect(Collectors.toList())
    }

    internal fun getMemberId(token: String): Long = parseClaims(token).get(this.memberIdHeader, Long::class.java)

    internal fun validateToken(token: String): Boolean {
        if (StringUtils.isEmpty(token)) return false

        return try {
            val claims = parseClaims(token)
            !claims.expiration.before(Date())
        } catch (e: MalformedJwtException) {
            false
        }
    }

    internal fun parseClaims(token: String): Claims = try {
        Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    } catch (e: ExpiredJwtException) {
        e.claims
    }

}