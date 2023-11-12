package com.better.betterbackend.global.exception.dto

import com.better.betterbackend.global.exception.ErrorCode
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ExceptionResponseDto<T>(

    val time: LocalDateTime = LocalDateTime.now(),

    val status: HttpStatus,

    val requestURI: String,

    val data: T,

) {

}