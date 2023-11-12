package com.better.betterbackend.domain.userrank.dto

import com.better.betterbackend.domain.user.dto.SimpleUserDto
import com.better.betterbackend.domain.userRankHistory.dto.SimpleUserRankHistoryDto
import com.better.betterbackend.userrank.domain.UserRank

data class UserRankDto(

    val id: Long,

    val score: Int,

    val user: SimpleUserDto,

    val userRankHistoryList: List<SimpleUserRankHistoryDto>,

    ) {
    constructor(userRank: UserRank) : this(
        userRank.id!!,
        userRank.score,
        SimpleUserDto(userRank.user!!),
        userRank.userRankHistoryList.map { SimpleUserRankHistoryDto(it) },
    )

}