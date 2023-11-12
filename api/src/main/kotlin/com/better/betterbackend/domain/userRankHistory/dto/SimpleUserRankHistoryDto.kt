package com.better.betterbackend.domain.userRankHistory.dto

import com.better.betterbackend.userrankhistory.domain.UserRankHistory

data class SimpleUserRankHistoryDto(

    val id: Long,

    val uid: Long,

    val score: Int,

    val description: String,

) {

    constructor(userRankHistory: UserRankHistory): this(
        userRankHistory.id!!,
        userRankHistory.uid,
        userRankHistory.score,
        userRankHistory.description,
    )

}