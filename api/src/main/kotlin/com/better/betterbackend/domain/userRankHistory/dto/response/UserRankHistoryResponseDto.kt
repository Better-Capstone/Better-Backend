package com.better.betterbackend.domain.userRankHistory.dto.response

import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto
import com.better.betterbackend.domain.userrank.dto.SimpleUserRankResponseDto
import com.better.betterbackend.userrankhistory.domain.UserRankHistory

class UserRankHistoryResponseDto (

    val id: Long,

    val uid: Long,

    val userRank: SimpleUserRankResponseDto,

    val study: SimpleStudyResponseDto,

    val score: Int,

    val description: String,

){

    constructor(userRankHistory: UserRankHistory): this(
        userRankHistory.id!!,
        userRankHistory.uid,
        SimpleUserRankResponseDto(userRankHistory.userRank),
        SimpleStudyResponseDto(userRankHistory.study),
        userRankHistory.score,
        userRankHistory.description,
    )

}