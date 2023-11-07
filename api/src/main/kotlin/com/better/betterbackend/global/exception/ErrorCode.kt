package com.better.betterbackend.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(

    val status: HttpStatus,

    val message: String,

) {

    KAKAO_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "카카오로부터 정보를 받아올 수 없습니다"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 토큰입니다"),
    JSON_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "잘못된 JSON 형식입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다"),
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "스터디를 찾을 수 없습니다"),
    ;

}
