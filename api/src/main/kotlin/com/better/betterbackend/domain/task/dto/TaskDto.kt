package com.better.betterbackend.domain.task.dto

import com.better.betterbackend.domain.challenge.dto.SimpleChallengeDto
import com.better.betterbackend.domain.member.dto.SimpleMemberDto
import com.better.betterbackend.domain.study.dto.SimpleStudyDto
import com.better.betterbackend.task.domain.Task
import java.time.LocalDateTime

data class TaskDto(

    var id: Long,

    val study: SimpleStudyDto,

    val member: SimpleMemberDto,

    val title: String,

    val deadline: LocalDateTime,

    val challenge: SimpleChallengeDto?,

    ){
    constructor(task: Task) : this(
        task.id!!,
        SimpleStudyDto(task.study),
        SimpleMemberDto(task.member),
        task.title,
        task.deadline,
        if (task.challenge != null) SimpleChallengeDto(task.challenge!!) else null,
    )
}
