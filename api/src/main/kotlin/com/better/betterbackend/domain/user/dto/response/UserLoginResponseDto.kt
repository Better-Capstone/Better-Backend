package com.better.betterbackend.domain.user.dto.response

import com.better.betterbackend.domain.user.dto.SimpleUserDto

class UserLoginResponseDto (

    val accessToken: String,

    val user: SimpleUserDto,

    ) {

}