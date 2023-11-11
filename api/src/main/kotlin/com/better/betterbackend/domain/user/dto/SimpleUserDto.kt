package com.better.betterbackend.domain.user.dto

import com.better.betterbackend.user.domain.User
import java.time.LocalDateTime

data class SimpleUserDto (

    val id: Long,

    val nickname: String,

    val name: String,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(user: User): this(
        user.id!!,
        user.nickname,
        user.name,
        user.createdAt,
        user.updatedAt
    )

}