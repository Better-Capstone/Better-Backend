package com.better.betterbackend.domain.user.dto

import com.better.betterbackend.user.domain.User

class UserNicknameScoreDto(

    val id : Long,

    val nickname : String,

    val score : Int,

) {

    constructor(user:User):this(
        user.id!!,
        user.nickname,
        user.userRank.score,
    )

}