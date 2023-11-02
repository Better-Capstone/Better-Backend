package com.better.betterbackend.domain.userRankHistory.dto

import com.better.betterbackend.domain.study.dto.SimpleStudyResponseDto
import com.better.betterbackend.domain.userrank.dto.SimpleUserRankResponseDto
import com.better.betterbackend.userrankhistory.domain.UserRankHistory

class UserRankHistoryResponseDto (

    var id: Long? = null,

    val uid: Long,

    val userRank: SimpleUserRankResponseDto,

    val study: SimpleStudyResponseDto,

    val score: Int,

    val description: String,

){

    constructor(uid: Long, userRankHistory: UserRankHistory): this(
        null,
        uid,
        SimpleUserRankResponseDto(userRankHistory.userRank),
        SimpleStudyResponseDto(userRankHistory.study),
        userRankHistory.score,
        userRankHistory.description,
    )

}