package com.better.betterbackend.domain.userrank.dto.response

import com.better.betterbackend.domain.user.dto.response.SimpleUserResponseDto
import com.better.betterbackend.domain.userRankHistory.dto.response.SimpleUserRankHistoryResponseDto
import com.better.betterbackend.userrank.domain.UserRank

class UserRankResponseDto(

    val id: Long,

    val score: Int,

    val user: SimpleUserResponseDto,

    val userRankHistoryList: List<SimpleUserRankHistoryResponseDto>,

) {
    constructor(userRank: UserRank) : this(
        userRank.id!!,
        userRank.score,
        SimpleUserResponseDto(userRank.user!!),
        userRank.userRankHistoryList.map { SimpleUserRankHistoryResponseDto(it) },
    )

}