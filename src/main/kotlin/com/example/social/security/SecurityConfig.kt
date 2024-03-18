package com.example.social.security

import com.example.social.security.oauth2.KakaoOauth2Service
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
class SecurityConfig (
    val jwtFilter: JwtFilter,
    val kakaoOauth2Service: KakaoOauth2Service
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .httpBasic { it.disable() }
        .csrf { it.disable() }
        .cors{}
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .authorizeHttpRequests { it.anyRequest().permitAll() }
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        .oauth2Login {it.userInfoEndpoint {it.userService(kakaoOauth2Service)}}
        .build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()

        config.allowCredentials = true
        config.allowedOrigins = listOf("http://localhost:3000")
        config.setAllowedMethods(listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"))
        config.allowedHeaders = listOf("*")
        config.exposedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

}