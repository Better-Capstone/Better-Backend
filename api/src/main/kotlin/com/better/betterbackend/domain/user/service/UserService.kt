package com.better.betterbackend.domain.user.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.domain.task.dto.response.SimpleTaskResponseDto
import com.better.betterbackend.domain.user.dto.response.SimpleUserResponseDto
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.better.betterbackend.domain.user.dto.response.UserResponseDto
import com.better.betterbackend.domain.userRankHistory.dto.response.UserRankHistoryResponseDto
import com.better.betterbackend.domain.userrank.dto.UserRankResponseDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.global.security.JwtTokenProvider
import com.better.betterbackend.user.dao.UserRepository
import com.better.betterbackend.user.domain.User
import com.better.betterbackend.userrank.dao.UserRankRepository
import com.better.betterbackend.userrank.domain.UserRank
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService (

    private val kakaoService: KakaoService,

    private val userRepository: UserRepository,

    private val userRankRepository: UserRankRepository,
  
    private val tokenProvider: JwtTokenProvider,

    // todo: 테스트 용도, 삭제 필요
    private val categoryRepository: CategoryRepository,
  
) {

    // todo: 테스트 용도, 삭제 필요
    fun test(nickname: String): String {
        val userRank = userRankRepository.save(UserRank(null, 4000, emptyList()))
        val user = userRepository.save(User(
            id = 1,
            nickname = nickname,
            name = "test",
            userRank = userRank,
        ))

        categoryRepository.save(Category(1, "string", emptyList()))

        return tokenProvider.createToken(user.id.toString())
    }

    fun register(request: UserRegisterRequestDto): UserRegisterResponseDto {
        val accessToken = request.accessToken
        val nickname = request.nickname

        val userInfo = kakaoService.getKakaoUserInfo(accessToken)

        // todo: userrank 저장 잘 되는지 확인 필요
        val userRank = userRankRepository.save(UserRank(
            score = 4000
        ))

        val user = userRepository.save(User(
            id = userInfo.id,
            nickname = nickname,
            name = userInfo.kakaoAccount.profile.nickname,
            userRank = userRank,
        ))

        return UserRegisterResponseDto(user)
    }

    fun login(accessToken: String): UserLoginResponseDto {
        val userInfo = kakaoService.getKakaoUserInfo(accessToken)

        val user = userRepository.findByIdOrNull(userInfo.id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserLoginResponseDto(tokenProvider.createToken(user.id.toString()), SimpleUserResponseDto(user))
    }

    fun getUser(id : Long): UserResponseDto {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserResponseDto(user)
    }

    fun getRank(id : Long) : UserRankResponseDto{
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserRankResponseDto(user.userRank)
    }

    fun getRankHistory(id: Long) : List<UserRankHistoryResponseDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return user.userRank.userRankHistoryList.map {
            UserRankHistoryResponseDto(user.id!!, it)
        }
    }

}
