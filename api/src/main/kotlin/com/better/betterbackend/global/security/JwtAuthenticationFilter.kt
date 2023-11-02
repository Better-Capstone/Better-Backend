package com.better.betterbackend.global.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter(

    private val jwtTokenProvider: JwtTokenProvider,

    ): GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token: String? = jwtTokenProvider.resolveToken((request as HttpServletRequest))

        if (token != null) {
            val split = token.split(" ")

            if (split.size == 2 && split[0] == "Bearer" && jwtTokenProvider.validateToken(split[1])) {
                val authentication = jwtTokenProvider.getAuthentication(split[1])
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        chain.doFilter(request, response)
    }

}