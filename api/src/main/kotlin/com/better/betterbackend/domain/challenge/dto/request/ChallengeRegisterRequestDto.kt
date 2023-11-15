package com.better.betterbackend.domain.challenge.dto.request

import java.time.LocalDateTime

data class ChallengeRegisterRequestDto (

    val title : String,

    val image : String,

    val description : String,

    val deadline: LocalDateTime,

){

}