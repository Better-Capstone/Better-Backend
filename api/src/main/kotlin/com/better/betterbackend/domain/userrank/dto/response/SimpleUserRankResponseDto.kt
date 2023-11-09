package com.better.betterbackend.domain.userrank.dto.response

import com.better.betterbackend.userrank.domain.UserRank

class SimpleUserRankResponseDto (

    val id: Long,

    val score: Int,

){
    constructor(userRank: UserRank): this(
        userRank.id!!,
        userRank.score
    )

}