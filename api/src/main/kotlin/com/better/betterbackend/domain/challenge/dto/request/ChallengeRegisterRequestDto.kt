package com.better.betterbackend.domain.challenge.dto.request

import org.apache.hc.core5.util.Deadline
import java.time.LocalDateTime

class ChallengeRegisterRequestDto (
    val studyId : Long,
    val taskId : Long,
    val title : String,
    val deadline: LocalDateTime
){
}