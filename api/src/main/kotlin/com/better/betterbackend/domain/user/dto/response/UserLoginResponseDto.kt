package com.better.betterbackend.domain.user.dto.response

import com.better.betterbackend.domain.user.dto.UserDto

class UserLoginResponseDto (
    val accessToken: String,
    val user: UserDto,
) {
}