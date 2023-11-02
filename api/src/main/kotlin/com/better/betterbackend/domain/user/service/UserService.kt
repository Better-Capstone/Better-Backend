package com.better.betterbackend.domain.user.service

import com.better.betterbackend.domain.member.dto.SimpleMemberResponseDto
import com.better.betterbackend.domain.user.dto.response.SimpleUserResponseDto
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.better.betterbackend.domain.user.dto.response.UserResponseDto
import com.better.betterbackend.domain.userRankHistory.dto.UserRankHistoryResponseDto
import com.better.betterbackend.domain.userrank.dto.UserRankResponseDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
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
) {

    fun register(request: UserRegisterRequestDto): UserRegisterResponseDto {
        val accessToken = request.accessToken
        val nickname = request.nickname

        val userInfo = kakaoService.getKakaoUserInfo(accessToken)


//        val user = userRepository.save(User(userInfo.id, nickname, userInfo.kakaoAccount.profile.nickname))//todo 생성자에 userrank 객체 만들어서 추가해줘야함
        val tempUser = User(userInfo.id, nickname, userInfo.kakaoAccount.profile.nickname)
        val userRank = userRankRepository.save(UserRank(null,4000,tempUser, emptyList()))//todo userrank저장 잘되는지 확인 필요
        val user = userRepository.save(User(userInfo.id, nickname, userInfo.kakaoAccount.profile.nickname,userRank))
        return UserRegisterResponseDto(user)
    }

    fun login(accessToken: String): UserLoginResponseDto {
        val userInfo = kakaoService.getKakaoUserInfo(accessToken)

        val user = userRepository.findByIdOrNull(userInfo.id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        // todo: jwt token 추가
        return UserLoginResponseDto("string", SimpleUserResponseDto(user))
    }
    fun getUser(id : Long): UserResponseDto {
        //유저의 id에 해당하는 db정보를 불러와서 dto로 감싸서 출력해줌.
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserResponseDto(user)
    }
    fun getRank(id : Long) : UserRankResponseDto{
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserRankResponseDto(user.userRank!!)
    }
    fun getRankHistory(id: Long) : List<UserRankHistoryResponseDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return user.userRank!!.userRankHistoryList.map {
            UserRankHistoryResponseDto(user.id!!, it)
        }

    }


}