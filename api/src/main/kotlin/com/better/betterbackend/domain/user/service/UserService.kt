package com.better.betterbackend.domain.user.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.category.domain.Category

import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.task.dto.response.TaskWithStudyResponseDto
import com.better.betterbackend.domain.user.dto.SimpleUserDto
import com.better.betterbackend.domain.user.dto.UserDto


import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.*
import com.better.betterbackend.domain.userRankHistory.dto.UserRankHistoryDto
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
            nickname = "test2",
            name = "test2",
            userRank = userRank2,
        )
        userRank2.user = user2
        userRepository.save(user2)

        val userRank3 = UserRank()
        val user3 = User(
            id = 3,
            nickname = "test3",
            name = "test3",
            userRank = userRank3,
        )
        userRank3.user = user3
        userRepository.save(user3)

        val userRank4 = UserRank()
        val user4 = User(
            id = 4,
            nickname = "test4",
            name = "test4",
            userRank = userRank4,
        )
        userRank4.user = user4
        userRepository.save(user4)

        val userRank5 = UserRank()
        val user5 = User(
            id = 5,
            nickname = "test5",
            name = "test5",
            userRank = userRank5,
        )
        userRank5.user = user5
        userRepository.save(user5)

        val userRank6 = UserRank()
        val user6 = User(
            id = 6,
            nickname = "test6",
            name = "test6",
            userRank = userRank6,
        )
        userRank6.user = user6
        userRepository.save(user6)

        val userRank7 = UserRank()
        val user7 = User(
            id = 7,
            nickname = "test7",
            name = "test7",
            userRank = userRank7,
        )
        userRank7.user = user7
        userRepository.save(user7)

        val userRank8 = UserRank()
        val user8 = User(
            id = 8,
            nickname = "test8",
            name = "test8",
            userRank = userRank8,
        )
        userRank8.user = user8
        userRepository.save(user8)

        val userRank9 = UserRank()
        val user9 = User(
            id = 9,
            nickname = "test9",
            name = "test9",
            userRank = userRank9,
        )
        userRank9.user = user9
        userRepository.save(user9)

        val userRank10 = UserRank()
        val user10 = User(
            id = 10,
            nickname = "test10",
            name = "test10",
            userRank = userRank10,
        )
        userRank10.user = user10
        userRepository.save(user10)


        categoryRepository.save(Category(1, "string", emptyList()))

        return listOf(tokenProvider.createToken(user1.id.toString()), tokenProvider.createToken(user2.id.toString()))
    }

    fun getToken(id: Long): String {
        return tokenProvider.createToken(id.toString())
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

        return UserRegisterResponseDto(user, tokenProvider.createToken(user.id.toString()))
    }

    fun login(accessToken: String): UserLoginResponseDto {
        val userInfo = kakaoService.getKakaoUserInfo(accessToken)
        val user = userRepository.findByIdOrNull(userInfo.id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserLoginResponseDto(tokenProvider.createToken(user.id.toString()), SimpleUserDto(user))
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

        return user.userRank.userRankHistoryList.map { UserRankHistoryDto(it) }
    }

    fun getTask(id: Long) : List<TaskDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        val list = ArrayList<TaskWithStudyResponseDto>()
        for (member:Member in user.memberList){
            for (task:Task in member.taskList){
                list.add(TaskWithStudyResponseDto(task, task.taskGroup.study!!))
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
