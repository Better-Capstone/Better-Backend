package com.better.betterbackend.domain.task.dto

import com.better.betterbackend.domain.challenge.dto.SimpleChallengeDto
import com.better.betterbackend.domain.member.dto.SimpleMemberDto
import com.better.betterbackend.domain.study.dto.SimpleStudyDto
import com.better.betterbackend.domain.taskgroup.dto.SimpleTaskGroupDto
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.taskgroup.domain.TaskGroup
import java.time.LocalDateTime

data class TaskDto(

    var id: Long,

    val taskGroup: SimpleTaskGroupDto,

    val member: SimpleMemberDto,

    val title: String,

    val challenge: SimpleChallengeDto?,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

){

    constructor(task: Task) : this(
        task.id!!,
        SimpleTaskGroupDto(task.taskGroup),
        SimpleMemberDto(task.member),
        task.title,
        if (task.challenge != null) SimpleChallengeDto(task.challenge!!) else null,
        task.createdAt,
        task.updatedAt,
    )
}
