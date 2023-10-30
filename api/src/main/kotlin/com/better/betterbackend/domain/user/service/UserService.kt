package com.better.betterbackend.domain.user.service

import com.better.betterbackend.domain.user.vo.UserInfoVo
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.better.betterbackend.domain.user.vo.OAuthTokenVo
import com.better.betterbackend.user.dao.UserRepository
import com.better.betterbackend.user.domain.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserService (
    private val userRepository: UserRepository,
) {

    fun getKakaoAuthToken(code: String): String {
        // todo: test 용 -> 삭제 필요
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
        restTemplate.setRequestFactory(HttpComponentsClientHttpRequestFactory())

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

    fun register(request: UserRegisterRequestDto): UserRegisterResponseDto {
        val accessToken = request.accessToken
        val nickname = request.nickname

        // todo: register error 처리 필요
        val userInfo = getKakaoUserInfo(accessToken)
        val user = userRepository.save(User(userInfo.id, nickname, userInfo.kakaoAccount.profile.nickname))

        return UserRegisterResponseDto(user)
    }

    fun login(accessToken: String): UserLoginResponseDto {
        val userInfo = getKakaoUserInfo(accessToken)

        // todo: login error 처리 필요
        val user = userRepository.findById(userInfo.id).get()

        // todo: jwt token 추가
        return UserLoginResponseDto("string", UserDto(user))
    }

}