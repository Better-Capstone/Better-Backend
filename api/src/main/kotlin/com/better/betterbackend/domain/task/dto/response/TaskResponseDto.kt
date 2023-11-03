package com.better.betterbackend.domain.task.dto

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.challenge.SimpleChallengeResponseDto
import com.better.betterbackend.domain.member.dto.SimpleMemberResponseDto
import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import jakarta.persistence.*
import java.time.LocalDateTime

class TaskResponseDto(

    var id: Long,

    val study: SimpleStudyResponseDto,

    val member: SimpleMemberResponseDto,

    val title: String,

    val deadline: LocalDateTime,

    val challenge: SimpleChallengeResponseDto,
){
    constructor(task: Task) : this(
        task.id,
        SimpleStudyResponseDto(task.study),
        SimpleMemberResponseDto(task.member),
        task.title,
        task.deadline,
        SimpleChallengeResponseDto(task.challenge),
    )
}
