package com.better.betterbackend.domain.user.dto.response

import com.better.betterbackend.user.domain.User

class UserRegisterResponseDto (

    val id: Long,

    val nickname: String,

    val accessToken: String,

) {

    constructor(user: User, accessToken: String) : this(
        user.id!!,
        user.nickname,
        accessToken
    )

}