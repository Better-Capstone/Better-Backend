package com.better.betterbackend.domain.userRankHistory.dto

import com.better.betterbackend.domain.study.dto.SimpleStudyDto
import com.better.betterbackend.domain.userrank.dto.SimpleUserRankDto
import com.better.betterbackend.userrankhistory.domain.UserRankHistory

data class UserRankHistoryDto (

    val id: Long,

    val uid: Long,

    val userRank: SimpleUserRankDto,

    val study: SimpleStudyDto,

    val score: Int,

    val description: String,

    ){

    constructor(userRankHistory: UserRankHistory): this(
        userRankHistory.id!!,
        userRankHistory.uid,
        SimpleUserRankDto(userRankHistory.userRank),
        SimpleStudyDto(userRankHistory.study),
        userRankHistory.score,
        userRankHistory.description,
    )

}