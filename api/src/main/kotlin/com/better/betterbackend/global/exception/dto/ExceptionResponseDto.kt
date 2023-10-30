package com.better.betterbackend.global.exception.dto

import com.better.betterbackend.global.exception.ErrorCode
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ExceptionResponseDto(

    val time: LocalDateTime,

    val status: HttpStatus,

    val message: String,

    val requestURI: String,

) {

    constructor(errorCode: ErrorCode, requestURI: String): this(
        LocalDateTime.now(), errorCode.status, errorCode.message, requestURI
    )

}