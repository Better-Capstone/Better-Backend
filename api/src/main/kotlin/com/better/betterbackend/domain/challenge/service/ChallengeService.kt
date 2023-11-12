package com.better.betterbackend.domain.challenge.service

import com.better.betterbackend.challenge.dao.ChallengeRepository
import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.challenge.dto.request.ChallengeRegisterRequestDto
import com.better.betterbackend.domain.challenge.dto.response.ChallengeResponseDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.member.dao.MemberRepository
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.task.dao.TaskRepository
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class ChallengeService(
    private val challengeRepository: ChallengeRepository,
    private val studyRepository: StudyRepository,
    private val memberRepository: MemberRepository,
    private val taskRepository: TaskRepository,

) {
   fun register(request : ChallengeRegisterRequestDto) : ChallengeResponseDto{
       val principal = SecurityContextHolder.getContext().authentication.principal
       val user = (principal as UserDetails) as User

       val studyId = request.studyId
       val taskId = request.taskId
       val title = request.title
       val deadline = request.deadline
       val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)
       val member = memberRepository.findByUserAndStudy(user,study) ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
       val task = taskRepository.findByMemberAndStudy(member,study) ?: throw CustomException(ErrorCode.TASK_NOT_FOUND)
       val challenge = Challenge(
           task = task,
           description = null, //todo description ,image 안들어오는데? 챌린지를 만드는데 image랑 description이 안들어오는게 이상함
           image = null,
           approveMember = emptyList(),
           rejectMember = emptyList(),
       )
       challengeRepository.save(challenge)
       return ChallengeResponseDto(challenge)

   }
    fun getChallenge(id : Long) : ChallengeResponseDto{
        val challenge = challengeRepository.findByIdOrNull(id) ?:throw CustomException(ErrorCode.CHALLENGE_NOT_FOUND)
        return ChallengeResponseDto(challenge)
    }
}







