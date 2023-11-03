package com.better.betterbackend.domain.challenge

import com.better.betterbackend.task.domain.Task
import jakarta.persistence.*

class ChallengeResponseDto (

    var id: Long? = null,

    val task: Task,

    val description: String,

    val image: String,

    val approveMember: List<Long>,

    val rejectMember: List<Long>,
){

}