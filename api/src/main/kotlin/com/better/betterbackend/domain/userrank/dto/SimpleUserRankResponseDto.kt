package com.better.betterbackend.domain.userrank.dto

import com.better.betterbackend.user.domain.User
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.userrankhistory.domain.UserRankHistory


class SimpleUserRankResponseDto (
    var id: Long? = null,

    val score: Int,


){
    constructor(userRank: UserRank): this(userRank.id, userRank.score)
}