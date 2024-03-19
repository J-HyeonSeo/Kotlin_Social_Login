package com.example.social.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
@RequiredArgsConstructor
class JwtFilter(val tokenProvider: TokenProvider) : OncePerRequestFilter() {

    val TOKEN_HEADER = "Authorization"
    val TOKEN_PREFIX = "Bearer "

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request, TOKEN_HEADER)

        if (StringUtils.hasText(token) && tokenProvider.validateToken(token!!)) {
            val authentication: Authentication = tokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response)
    }

    internal fun resolveToken(request: HttpServletRequest, tokenHeader: String): String? {
        val token = request.getHeader(tokenHeader)
        return if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            token.substring(TOKEN_PREFIX.length)
        } else null
    }

}