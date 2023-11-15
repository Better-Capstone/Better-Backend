package com.better.betterbackend.domain.task.dto.request

import java.time.LocalDateTime

class TaskRegisterRequestDto (
    val studyId : Long,
    val title : String,
    val deadline : LocalDateTime
){

}