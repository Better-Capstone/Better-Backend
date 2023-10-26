package com.better.betterbackend.domain.user.dto

import com.better.betterbackend.domain.user.domain.User
import java.time.LocalDateTime

class UserDto (
    val id: Long,
    val nickname: String,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    constructor(user: User): this(user.id!!, user.nickname, user.name, user.createdAt, user.updatedAt)

}