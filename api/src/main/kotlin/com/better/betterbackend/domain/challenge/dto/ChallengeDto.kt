package com.better.betterbackend.domain.challenge.dto

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.task.dto.SimpleTaskDto
import java.time.LocalDateTime

data class ChallengeDto (

    var id: Long,

    val task: SimpleTaskDto,

    val description: String,

    val image: String,

    val approveMember: List<Long>,

    val rejectMember: List<Long>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

){

    constructor(challenge: Challenge): this (
        challenge.id!!,
        SimpleTaskDto(challenge.task),
        challenge.description,
        challenge.image,
        challenge.approveMember,
        challenge.rejectMember,
        challenge.createdAt,
        challenge.updatedAt,
    )

}
