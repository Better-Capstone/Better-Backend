
package com.better.betterbackend.domain.challenge.dto


import com.better.betterbackend.challenge.domain.Challenge
import java.time.LocalDateTime

data class SimpleChallengeDto (

    var id: Long,

    val description: String,

    val image: String,

    val approveMember: List<Long>,

    val rejectMember: List<Long>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

){

    constructor(challenge: Challenge) : this (
        challenge.id!!,
        challenge.description,
        challenge.image,
        challenge.approveMember,
        challenge.rejectMember,
        challenge.createdAt,
        challenge.updatedAt,
    )

}
