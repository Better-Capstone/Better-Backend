package com.better.betterbackend.domain.userrank.dto

import com.better.betterbackend.userrank.domain.UserRank

data class SimpleUserRankDto (

    val id: Long,

    val score: Int,

){
    constructor(userRank: UserRank): this(
        userRank.id!!,
        userRank.score
    )

}