package com.better.betterbackend.domain.task.service

import com.better.betterbackend.domain.task.dto.request.TaskRegisterRequestDto
import com.better.betterbackend.domain.task.dto.response.TaskDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.member.dao.MemberRepository
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.task.dao.TaskRepository
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.task.domain.TaskStatus
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class TaskService (
    private val taskRepository : TaskRepository,
    private val studyRepository : StudyRepository,
    private val memberRepository: MemberRepository,
){
    fun register(request : TaskRegisterRequestDto) : TaskDto{
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val studyId = request.studyId
        val title = request.title
        val deadline = request.deadline
        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)
        val member = memberRepository.findByUserAndStudy(user, study) ?:throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
        // 유저도 있고 스터디도 있는데 유저가 그 스터디에 가입 안했으면 멤버가 없겠지 -> 그러면 null이 나올거 -> 에러코드 추가해서 throw CustomException
        // 챌린지 생성은 따로 태스크 만들고 그 태스크 수행해서 나 다했다고 인증 올릴때 그게 챌린지


        val task = Task(
            study = study,
            member = member,
            title = title,
            deadline = deadline,
            challenge = null,
        )
        taskRepository.save(task)
        return TaskDto(task)

    }


    fun getTask(studyId:Long) : List<TaskDto>{
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User
        val study = studyRepository.findByIdOrNull(studyId)?:throw CustomException(ErrorCode.STUDY_NOT_FOUND)
//        val inprogressTask= study.taskList.find { it.status == TaskStatus.INPROGRESS } ?: throw CustomException(ErrorCode.TASK_NOT_FOUND)


        val inprogressTasks = study.taskList.filter { it.status == TaskStatus.INPROGRESS } //진행중인 태스크가 여러개일경우

//        val member = memberRepository.findByUserAndStudy(user,study!!) ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
//        val task = taskRepository.findByMemberAndStudy(member,study)?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)
        //todo member , study 로 일치하는 task가 두개가 되면 문제가 생김 status 추가 고려 -> studyid로 찾은 study의 tasklist 중 inprogress인 task를 반환

        return inprogressTasks.map { TaskDto(it) }


    }
}