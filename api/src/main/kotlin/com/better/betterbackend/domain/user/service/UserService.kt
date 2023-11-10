package com.better.betterbackend.domain.user.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.domain.challenge.response.ChallengeResponseDto
import com.better.betterbackend.domain.task.dto.response.TaskResponseDto
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.*
import com.better.betterbackend.domain.userRankHistory.dto.response.UserRankHistoryResponseDto
import com.better.betterbackend.domain.userrank.dto.response.UserRankResponseDto
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

    // todo: 테스트 용도, 삭제 필요
    private val categoryRepository: CategoryRepository,
  
) {

    // todo: 테스트 용도, 삭제 필요
    fun test(): List<String> {
        val userRank1 = UserRank()
        val user1 = User(
            id = 1,
            nickname = "test1",
            name = "test1",
            userRank = userRank1,
        )
        userRank1.user = user1
        userRepository.save(user1)

        val userRank2 = UserRank()
        val user2 = User(
            id = 2,
            nickname = "test1",
            name = "test2",
            userRank = userRank2,
        )
        userRank2.user = user2
        userRepository.save(user2)

        categoryRepository.save(Category(1, "string", emptyList()))

        return listOf(tokenProvider.createToken(user1.id.toString()), tokenProvider.createToken(user2.id.toString()))
    }

    fun check(id: Long): UserCheckResponseDto {
        userRepository.findByIdOrNull(id) ?: return UserCheckResponseDto(false)

        return UserCheckResponseDto(true)
    }

    fun register(request: UserRegisterRequestDto): UserRegisterResponseDto {
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

    fun getRank(id : Long) : UserRankResponseDto {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserRankResponseDto(user.userRank)
    }

    fun getRankHistory(id: Long) : List<UserRankHistoryResponseDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return user.userRank.userRankHistoryList.map { UserRankHistoryResponseDto(it) }
    }

    fun getTask(id: Long) : List<TaskResponseDto>{
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        val list = ArrayList<TaskResponseDto>()
        for (member:Member in user.memberList){
            for (task:Task in member.taskList){
                list.add(TaskResponseDto(task))
            }
        }

        return list
    }

    fun getChallenge(id: Long) : List<ChallengeResponseDto>{
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        val list = ArrayList<ChallengeResponseDto>()
        for (member : Member in user.memberList) {
            for (task: Task in member.taskList) {
                list.add(ChallengeResponseDto(task.challenge))
            }
        }

        return list
    }

}
