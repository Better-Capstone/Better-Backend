package com.better.betterbackend.domain.user.service

import com.better.betterbackend.domain.user.vo.UserInfoVo
import com.better.betterbackend.global.exception.handler.KakaoErrorHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.*
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class KakaoService {

    fun getKakaoUserInfo(accessToken: String): UserInfoVo {
        val restTemplate = RestTemplate()
        restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
        restTemplate.errorHandler = KakaoErrorHandler()

        val url = "https://kapi.kakao.com/v2/user/me"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.set("Authorization", "Bearer $accessToken")

        val requestBody = "property_keys=[\"kakao_account.profile\"]"
        val requestEntity = HttpEntity(requestBody, headers)
        val responseEntity: ResponseEntity<String> = restTemplate.postForEntity(url, requestEntity, String::class.java)

        val jsonResponse = responseEntity.body
        val objectMapper = ObjectMapper().registerModule(KotlinModule())

        return objectMapper.readValue(jsonResponse, UserInfoVo::class.java)
    }

}