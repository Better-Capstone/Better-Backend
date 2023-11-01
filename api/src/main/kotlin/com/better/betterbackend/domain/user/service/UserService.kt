package com.better.betterbackend.domain.user.service

import com.better.betterbackend.domain.user.vo.UserInfoVo
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.better.betterbackend.domain.user.vo.OAuthTokenVo
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.global.exception.handler.KakaoErrorHandler
import com.better.betterbackend.user.dao.UserRepository
import com.better.betterbackend.user.domain.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.*
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserService (
    private val kakaoService: KakaoService,
    private val userRepository: UserRepository,
) {

    fun register(request: UserRegisterRequestDto): UserRegisterResponseDto {
        val accessToken = request.accessToken
        val nickname = request.nickname

        val userInfo = kakaoService.getKakaoUserInfo(accessToken)
        val user = userRepository.save(User(userInfo.id, nickname, userInfo.kakaoAccount.profile.nickname))

        return UserRegisterResponseDto(user)
    }

    fun login(accessToken: String): UserLoginResponseDto {
        val userInfo = kakaoService.getKakaoUserInfo(accessToken)

        val user = userRepository.findByIdOrNull(userInfo.id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        // todo: jwt token 추가
        return UserLoginResponseDto("string", UserDto(user))
    }

}