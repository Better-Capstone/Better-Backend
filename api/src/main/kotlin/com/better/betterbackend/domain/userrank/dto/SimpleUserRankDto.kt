package com.better.betterbackend.domain.userrank.dto

import com.better.betterbackend.userrank.domain.UserRank
import java.time.LocalDateTime

data class SimpleUserRankDto (

    val id: Long,

    val score: Int,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

){
    constructor(userRank: UserRank): this(
        userRank.id!!,
        userRank.score,
        userRank.createdAt,
        userRank.updatedAt,
    )

}