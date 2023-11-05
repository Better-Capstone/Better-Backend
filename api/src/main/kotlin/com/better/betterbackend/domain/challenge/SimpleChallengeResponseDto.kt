package com.better.betterbackend.domain.challenge

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.task.dto.response.SimpleTaskResponseDto

class SimpleChallengeResponseDto (

    var id: Long,

    val description: String,

    val image: String,

    val approveMember: List<Long>,

    val rejectMember: List<Long>,

){

    constructor(challenge: Challenge) : this (
        challenge.id!!,
        challenge.description,
        challenge.image,
        challenge.approveMember,
        challenge.rejectMember,
    )

}
