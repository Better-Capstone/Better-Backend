package com.better.betterbackend.domain.userRankHistory.dto.response

import com.better.betterbackend.userrankhistory.domain.UserRankHistory

class SimpleUserRankHistoryResponseDto(

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