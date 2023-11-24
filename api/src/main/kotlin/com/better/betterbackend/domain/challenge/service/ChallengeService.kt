package com.better.betterbackend.domain.challenge.service

import com.better.betterbackend.challenge.dao.ChallengeRepository
import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeApproveRequestDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeRegisterRequestDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.task.dao.TaskRepository
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class ChallengeService (

    private val challengeRepository: ChallengeRepository,

    private val taskRepository: TaskRepository,

) {

    fun register(request: ChallengeRegisterRequestDto, taskId: Long): ChallengeDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val image = request.image
        val description = request.description
        val task = taskRepository.findByIdOrNull(taskId) ?: throw CustomException(ErrorCode.TASK_NOT_FOUND)

        // 로그인한 유저가 해당 태스크의 주인이 아닌 경우
        if (task.member.user.id!! != user.id!!) {
            throw CustomException(ErrorCode.NOT_YOUR_TASK)
        }

        // 이미 챌린지가 등록되어 있는 태스크의 경우
        if (task.challenge != null) {
            throw CustomException(ErrorCode.CHALLENGE_ALREADY_REGISTERED)
        }

        val challenge = Challenge(
           task = task,
           description = description,
           image = image,
           approve = ArrayList(),
           reject = ArrayList(),
        )

        task.challenge = challenge
        taskRepository.save(task)

        return ChallengeDto(challenge)
    }

    fun getChallenge(id: Long): ChallengeDto {
        val challenge = challengeRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.CHALLENGE_NOT_FOUND)

        return ChallengeDto(challenge)
    }

    fun approve(id: Long, request: ChallengeApproveRequestDto) {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val challenge = challengeRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.CHALLENGE_NOT_FOUND)

        // 챌린지에 스스로 승인/거절을 하려 할 경우
        if (challenge.task.member.user.id!! == user.id!!) {
            throw CustomException(ErrorCode.SELF_APPROVE_NOT_POSSIBLE)
        }

        // 스터디에 참여하지 않은 유저가 승인/거절하려 하는 경우
        val memberList = challenge.task.taskGroup.study!!.memberList
        memberList.find { it.user.id!! == user.id!! } ?: throw CustomException(ErrorCode.NOT_PARTICIPATED)

        // 이미 승인/거절을 한 멤버일 경우
        if (challenge.approve.find { it == user.id!! } != null
            || challenge.reject.find { it == user.id!! } != null) {
            throw CustomException(ErrorCode.ALREADY_APPROVED_MEMBER)
        }

        if (request.approved) {
            challenge.approve += user.id!!
        } else {
            challenge.reject += user.id!!
        }

        challengeRepository.save(challenge)
    }

}