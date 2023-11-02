package com.better.betterbackend.domain.user.dto.response

class UserLoginResponseDto (
    val accessToken: String,
    val user: SimpleUserResponseDto,
) {
}