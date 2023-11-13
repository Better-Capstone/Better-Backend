package com.better.betterbackend.domain.challenge.dto.request


import java.time.LocalDateTime

class ChallengeRegisterRequestDto (
    val studyId : Long,
    val taskId : Long,
    val title : String,
    val deadline: LocalDateTime
){
}