package com.better.betterbackend.domain.task.dto.response

import com.better.betterbackend.domain.challenge.dto.response.SimpleChallengeResponseDto
import com.better.betterbackend.domain.member.dto.response.SimpleMemberResponseDto
import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto
import com.better.betterbackend.task.domain.Task
import java.time.LocalDateTime

class TaskDto(

    var id: Long,

    val study: SimpleStudyResponseDto,

    val member: SimpleMemberResponseDto,

    val title: String,

    val deadline: LocalDateTime,

    val challenge: SimpleChallengeResponseDto,

    ){
    constructor(task: Task) : this(
        task.id!!,
        SimpleStudyResponseDto(task.study),
        SimpleMemberResponseDto(task.member),
        task.title,
        task.deadline,
        SimpleChallengeResponseDto(task.challenge!!),
    )
}
