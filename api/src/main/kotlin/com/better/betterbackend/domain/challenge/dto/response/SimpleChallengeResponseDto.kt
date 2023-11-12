
package com.better.betterbackend.domain.challenge.dto.response

import com.better.betterbackend.challenge.domain.Challenge

data class SimpleChallengeDto (

    var id: Long,

    val description: String?,//todo image description nullable 확인 필요

    val image: String?,

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
