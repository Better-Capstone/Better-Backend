package com.better.betterbackend.domain.user.service

import com.better.betterbackend.domain.user.vo.OAuthTokenVo
import com.better.betterbackend.domain.user.vo.UserInfoVo
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.global.exception.handler.KakaoErrorHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.*
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class KakaoService {

    // todo: test 용 -> 삭제 필요
    fun getKakaoAuthToken(code: String): String {
        // 카카오 인증토큰 받기
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val requestBody = "grant_type=authorization_code" +
                "&client_id=8d5ea40d2be91e2927e726e69eeda1b5" +
                "&redirect_uri=http://127.0.0.1:8080/auth/kakao/callback" +
                "&code=$code"
        val requestEntity = HttpEntity(requestBody, headers)

        val url = "https://kauth.kakao.com/oauth/token"

        val responseEntity: ResponseEntity<String> = restTemplate.postForEntity(url, requestEntity, String::class.java)

        // 인증토큰 받아서 OAuthToken에 저장하기
        val jsonResponse = responseEntity.body
        val objectMapper = ObjectMapper()
        val oAuthToken = objectMapper.readValue(jsonResponse, OAuthTokenVo::class.java)

        return oAuthToken.access_token!!
    }

    fun getKakaoUserInfo(accessToken: String): UserInfoVo {
        val restTemplate = RestTemplate()
        restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
        restTemplate.errorHandler = KakaoErrorHandler()

        val url = "https://kapi.kakao.com/v2/user/me"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.set("Authorization", "Bearer ${accessToken}")

        val requestBody = "property_keys=[\"kakao_account.profile\"]"
        val requestEntity = HttpEntity(requestBody, headers)
        val responseEntity: ResponseEntity<String> = restTemplate.postForEntity(url, requestEntity, String::class.java)

        val jsonResponse = responseEntity.body
        val objectMapper = ObjectMapper().registerModule(KotlinModule())

        return objectMapper.readValue(jsonResponse, UserInfoVo::class.java)
    }

}