package com.better.betterbackend.domain.challenge.response

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.task.dto.response.SimpleTaskResponseDto

class ChallengeResponseDto (

    var id: Long,

    val task: SimpleTaskResponseDto,

    val description: String,

    val image: String,

    val approveMember: List<Long>,

    val rejectMember: List<Long>,

){

    constructor(challenge: Challenge) :this (
        challenge.id!!,
        SimpleTaskResponseDto(challenge.task),
        challenge.description,
        challenge.image,
        challenge.approveMember,
        challenge.rejectMember,
    )

}
