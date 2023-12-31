package com.better.betterbackend.domain.user.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.task.dto.response.TaskStudyUserResponseDto
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.*
import com.better.betterbackend.domain.userRankHistory.dto.UserRankHistoryDto
import com.better.betterbackend.domain.userRankHistory.dto.UserRankHistoryStudyDto
import com.better.betterbackend.domain.userrank.dto.UserRankDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.global.security.JwtTokenProvider
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.user.dao.UserRepository
import com.better.betterbackend.user.domain.User
import com.better.betterbackend.userrank.domain.UserRank
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService (

    private val kakaoService: KakaoService,

    private val userRepository: UserRepository,
  
    private val tokenProvider: JwtTokenProvider,

    private val categoryRepository: CategoryRepository,

) {

    fun test(): String {
        val category = Category(
            name = "test1"
        )

        categoryRepository.save(category)

        val userRank = UserRank()
        val user = User(
            id = 1,
            nickname = "test1",
            name = "test1",
            userRank = userRank,
        )

        userRank.user = user
        userRepository.save(user)

        return tokenProvider.createToken(user.id.toString())
    }

    fun getToken(id: Long): String {
        return tokenProvider.createToken(id.toString())
    }

    fun check(id: Long): UserCheckResponseDto {
        userRepository.findByIdOrNull(id) ?: return UserCheckResponseDto(false)

        return UserCheckResponseDto(true)
    }

    fun register(request: UserRegisterRequestDto): UserRegisterAndLoginResponseDto {
        val accessToken = request.accessToken!!
        val nickname = request.nickname!!

        val userInfo = kakaoService.getKakaoUserInfo(accessToken)

        // 이미 해당 id로 가입된 유저가 있는 경우
        if (userRepository.findByIdOrNull(userInfo.id) != null) {
            throw CustomException(ErrorCode.USER_ALREADY_EXIST)
        }

        val userRank = UserRank()
        val user = User(
            id = userInfo.id,
            nickname = nickname,
            name = userInfo.kakaoAccount.profile.nickname,
            userRank = userRank,
        )

        userRank.user = user
        userRepository.save(user)

        return UserRegisterAndLoginResponseDto(tokenProvider.createToken(user.id.toString()), UserDto(user))
    }

    fun login(accessToken: String): UserRegisterAndLoginResponseDto {
        val userInfo = kakaoService.getKakaoUserInfo(accessToken)
        val user = userRepository.findByIdOrNull(userInfo.id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserRegisterAndLoginResponseDto(tokenProvider.createToken(user.id.toString()), UserDto(user))
    }

    fun getUser(id : Long): UserDto {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserDto(user)
    }

    fun getRank(id : Long) : UserRankDto {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserRankDto(user.userRank)
    }

    fun getRankHistory(id: Long) : List<UserRankHistoryDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return user.userRank.userRankHistoryList.map { UserRankHistoryStudyDto(it, it.task!!.taskGroup.study!!) }
    }

    fun getTask(id: Long) : List<TaskDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        val list = ArrayList<TaskStudyUserResponseDto>()
        for (member:Member in user.memberList){
            for (task:Task in member.taskList){
                list.add(TaskStudyUserResponseDto(task, task.taskGroup.study!!, user))
            }
        }

        return list
    }

    fun getChallenge(id: Long) : List<ChallengeDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        val list = ArrayList<ChallengeDto>()
        for (member : Member in user.memberList) {
            for (task: Task in member.taskList) {
                list.add(ChallengeDto(task.challenge!!))
            }
        }

        return list
    }

}
