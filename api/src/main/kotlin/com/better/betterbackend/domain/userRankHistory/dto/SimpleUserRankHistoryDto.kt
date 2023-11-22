package com.better.betterbackend.domain.userRankHistory.dto

import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import java.time.LocalDateTime

data class SimpleUserRankHistoryDto(

    val id: Long,

    val score: Int,

    val description: String,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(userRankHistory: UserRankHistory): this(
        userRankHistory.id!!,
        userRankHistory.score,
        userRankHistory.description,
        userRankHistory.createdAt,
        userRankHistory.updatedAt,
    )

}