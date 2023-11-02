package com.better.betterbackend.domain.userrank.dto

import com.better.betterbackend.userrank.domain.UserRank

class SimpleUserRankResponseDto (

    var id: Long? = null,

    val score: Int,

){
    constructor(userRank: UserRank): this(userRank.id, userRank.score)

}