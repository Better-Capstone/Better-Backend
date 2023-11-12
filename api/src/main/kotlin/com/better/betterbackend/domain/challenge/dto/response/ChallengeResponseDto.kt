
package com.better.betterbackend.domain.challenge.dto.response

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.task.dto.SimpleTaskDto

data class ChallengeDto (

    var id: Long,

    val task: SimpleTaskDto,

    val description: String?,

    val image: String?,

    val approveMember: List<Long>,

    val rejectMember: List<Long>,

){

    constructor(challenge: Challenge) :this (//todo 나중에 description image 어떻게 할지 확인후에 nullable 여부 변경
        challenge.id!!,
        SimpleTaskDto(challenge.task),
        challenge.description,
        challenge.image,
        challenge.approveMember,
        challenge.rejectMember,
    )

}
