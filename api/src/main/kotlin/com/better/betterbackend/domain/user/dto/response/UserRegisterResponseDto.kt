package com.better.betterbackend.domain.user.dto.response

import com.better.betterbackend.user.domain.User

class UserRegisterResponseDto (

    val id: Long,

    val nickname: String,

) {

    constructor(user: User) : this(user.id!!, user.nickname)

}