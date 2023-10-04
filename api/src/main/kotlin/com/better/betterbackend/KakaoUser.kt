package com.better.betterbackend

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
@RestController
class KakaoUser {
    @GetMapping("/auth/kakao/userinfo")
    fun kakaoUser(@RequestParam("access_token") access_token : String): ResponseEntity<String> {
        val restTemplate = RestTemplate()
        val url = "https://kapi.kakao.com/v2/user/me"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.set("Authorization","Bearer $access_token")
        val requestEntity = HttpEntity<String>(headers)
        val responseEntity: ResponseEntity<String> = restTemplate.postForEntity(url, requestEntity, String::class.java)

        return responseEntity
//        return "응애"
    }
}