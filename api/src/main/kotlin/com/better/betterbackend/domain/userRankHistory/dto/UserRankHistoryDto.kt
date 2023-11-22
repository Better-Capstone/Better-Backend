package com.better.betterbackend.domain.userRankHistory.dto

import com.better.betterbackend.domain.study.dto.SimpleStudyDto
import com.better.betterbackend.domain.task.dto.SimpleTaskDto
import com.better.betterbackend.domain.userrank.dto.SimpleUserRankDto
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import java.time.LocalDateTime

data class UserRankHistoryDto (

    val id: Long,

    val score: Int,

    val description: String,

    val userRank: SimpleUserRankDto,

    val task: SimpleTaskDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

){

    constructor(userRankHistory: UserRankHistory): this(
        userRankHistory.id!!,
        userRankHistory.score,
        userRankHistory.description,
        SimpleUserRankDto(userRankHistory.userRank),
        SimpleTaskDto(userRankHistory.task),
        userRankHistory.createdAt,
        userRankHistory.updatedAt,
    )

}