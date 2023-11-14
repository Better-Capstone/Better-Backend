package com.better.betterbackend.domain.challenge.service

import com.better.betterbackend.challenge.dao.ChallengeRepository
import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeApproveRequestDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeRegisterRequestDto

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
   fun register(request : ChallengeRegisterRequestDto,taskId :Long) : ChallengeDto{
       val principal = SecurityContextHolder.getContext().authentication.principal
       val user = (principal as UserDetails) as User

//       val studyId = request.studyId
//       val taskId = request.taskId
       val title = request.title
       val deadline = request.deadline
       val image = request.image
       val description = request.description
//       val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)
//       val member = memberRepository.findByUserAndStudy(user,study) ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
//       val task = taskRepository.findByMemberAndStudy(member,study) ?: throw CustomException(ErrorCode.TASK_NOT_FOUND)
       val task = taskRepository.findByIdOrNull(taskId) ?:throw CustomException(ErrorCode.TASK_NOT_FOUND)
       val challenge = Challenge(
           task = task,
           description = description,
           image = image,
           approveMember = emptyList(),
           rejectMember = emptyList(),
       )

       task.challenge = challenge
       taskRepository.save(task)
       //todo challenge를 만들었는데 task.challenge 의 null값이 변경되지않음 -> task에 챌린지를 넣어서 save해주면 알아서 challenge도 세이브가 된다
//       challengeRepository.save(challenge)
       return ChallengeDto(challenge)

   }
    fun getChallenge(id : Long) : ChallengeDto{
        val challenge = challengeRepository.findByIdOrNull(id) ?:throw CustomException(ErrorCode.CHALLENGE_NOT_FOUND)
        return ChallengeDto(challenge)
    }
    fun approve(id : Long,request: ChallengeApproveRequestDto) {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val challenge = challengeRepository.findByIdOrNull(id)?: throw CustomException(ErrorCode.CHALLENGE_NOT_FOUND)

        if (request.approved==true){
            val newList = challenge.approveMember.plusElement(user.id!!)//리스트에항목 추가
            challenge.approveMember = newList
            challengeRepository.save(challenge)
        }
        else{
            val newList = challenge.rejectMember.plusElement(user.id!!)
            challenge.rejectMember = newList
            challengeRepository.save(challenge)
        }
    }
}







