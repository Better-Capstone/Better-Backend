package com.better.betterbackend.domain.challenge.dto.request


import java.time.LocalDateTime

class ChallengeRegisterRequestDto (

    val title : String,
    val image : String,
    val description : String,
    val deadline: LocalDateTime,
){
}