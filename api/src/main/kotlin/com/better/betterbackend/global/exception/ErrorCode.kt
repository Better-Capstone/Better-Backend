package com.better.betterbackend.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(

    val status: HttpStatus,

    val message: String,

) {

    KAKAO_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "존재하지 않는 토큰입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다"),

}