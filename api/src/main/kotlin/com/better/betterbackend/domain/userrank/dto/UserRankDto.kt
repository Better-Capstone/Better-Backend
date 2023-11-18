package com.better.betterbackend.domain.userrank.dto

import com.better.betterbackend.domain.user.dto.SimpleUserDto
import com.better.betterbackend.domain.userRankHistory.dto.SimpleUserRankHistoryDto
import com.better.betterbackend.userrank.domain.UserRank
import java.time.LocalDateTime

data class UserRankDto(

    val id: Long,

    val score: Int,

    val user: SimpleUserDto,

    val userRankHistoryList: List<SimpleUserRankHistoryDto>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {
    constructor(userRank: UserRank) : this(
        userRank.id!!,
        userRank.score,
        SimpleUserDto(userRank.user!!),
        userRank.userRankHistoryList.map { SimpleUserRankHistoryDto(it) },
        userRank.createdAt,
        userRank.updatedAt,
    )

}