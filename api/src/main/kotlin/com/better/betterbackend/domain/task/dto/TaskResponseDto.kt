package com.better.betterbackend.domain.task.dto

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import jakarta.persistence.*
import java.time.LocalDateTime

class TaskResponseDto(

    var id: Long? = null,

    val study: Study,

    val member: Member,

    val title: String,

    val deadline: LocalDateTime,

    val challenge: Challenge,
    ){
    constructor(task: Task) : this()
}
