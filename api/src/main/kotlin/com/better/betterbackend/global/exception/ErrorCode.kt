package com.better.betterbackend.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(

    val status: HttpStatus,

    val message: String,

) {

    KAKAO_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "카카오로부터 정보를 받아올 수 없습니다"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 토큰입니다"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다"),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 태스크를 찾을수 없습니다"),
    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 챌린지를 찾을수 없습니다"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다"),
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 스터디를 찾을 수 없습니다"),

    JSON_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "잘못된 JSON 형식입니다"),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 가입된 유저입니다"),
    OVER_CAPACITY(HttpStatus.BAD_REQUEST, "해당 스터디의 정원을 초과하였습니다"),
    ALREADY_PARTICIPATED(HttpStatus.BAD_REQUEST, "해당 스터디에 이미 가입된 상태입니다"),
    NOT_PARTICIPATED(HttpStatus.BAD_REQUEST, "가입하지 않은 스터디입니다"),
    UNDER_MIN_RANK(HttpStatus.BAD_REQUEST, "해당 스터디의 최저 점수를 충족하지 못했습니다"),
    NOT_YOUR_TASK(HttpStatus.BAD_REQUEST, "해당 태스크의 주인이 아닙니다"),
    SELF_APPROVE_NOT_POSSIBLE(HttpStatus.BAD_REQUEST, "자신의 챌린지에 스스로 인증할 수 없습니다"),
    ALREADY_APPROVED_MEMBER(HttpStatus.BAD_REQUEST, "이미 해당 태스크에 대해 인증을 한 멤버입니다"),
    CHALLENGE_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 챌린지가 등록되어 있는 태스크입니다"),
    ;

}
