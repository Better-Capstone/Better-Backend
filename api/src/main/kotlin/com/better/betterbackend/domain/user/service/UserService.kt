package com.better.betterbackend.domain.user.service

import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.global.security.JwtTokenProvider
import com.better.betterbackend.user.dao.UserRepository
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService (

    private val kakaoService: KakaoService,

    private val userRepository: UserRepository,

    private val tokenProvider: JwtTokenProvider,

    ) {

    fun test(nickname: String): String {
        val user = userRepository.save(User(1, nickname, "test"))

        return tokenProvider.createToken(user.id.toString())
    }

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

        return UserLoginResponseDto(tokenProvider.createToken(user.id.toString()), UserDto(user))
    }

    fun hello(): String {
        return "hello"
    }

}