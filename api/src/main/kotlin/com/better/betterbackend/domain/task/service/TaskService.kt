package com.better.betterbackend.domain.task.service

import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.task.dto.request.TaskRegisterRequestDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.task.dao.TaskRepository
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class TaskService (

    private val taskRepository : TaskRepository,

    private val studyRepository : StudyRepository,

){

    fun register(request : TaskRegisterRequestDto): TaskDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val studyId = request.studyId
        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        // 해당 스터디에 참여하지 않은 유저인 경우
        val member = study.memberList.find { it.user.id!! == user.id!! } ?: throw CustomException(ErrorCode.NOT_PARTICIPATED)

        val taskGroup = study.taskGroupList.find { it.status == TaskGroupStatus.INPROGRESS }!!

        val task = Task(
            taskGroup = taskGroup,
            member = member,
            title = request.title,
            challenge = null,
        )
        taskRepository.save(task)

        return TaskDto(task)
    }

}