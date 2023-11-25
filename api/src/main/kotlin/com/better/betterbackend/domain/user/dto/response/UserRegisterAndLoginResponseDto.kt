package com.better.betterbackend.domain.user.dto.response

import com.better.betterbackend.domain.user.dto.UserDto

class UserRegisterAndLoginResponseDto (

    val accessToken: String,

    val user: UserDto,

) {

}