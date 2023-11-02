package com.better.betterbackend.global.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig (

    private val tokenProvider: JwtTokenProvider,

    ) {

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf {
            it.disable()
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authorizeHttpRequests {
            it.requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**", "/user/login", "/user/register", "/user/test/**").permitAll()
                .anyRequest().authenticated()
        }
        .addFilterBefore(JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter::class.java)
        .exceptionHandling{
            it.authenticationEntryPoint(JwtAuthenticationEntryPoint())
        }
        .build()!!

}