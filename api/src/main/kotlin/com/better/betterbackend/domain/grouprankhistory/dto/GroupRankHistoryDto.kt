package com.better.betterbackend.domain.grouprankhistory.dto

import com.better.betterbackend.domain.grouprank.dto.SimpleGroupRankDto
import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import java.time.LocalDateTime

data class GroupRankHistoryDto (

    val id: Long,

    val score: Int,

    val totalNumber: Int,

    val participantsNumber: Number,

    val groupRank: SimpleGroupRankDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(groupRankHistory: GroupRankHistory): this(
        groupRankHistory.id!!,
        groupRankHistory.score,
        groupRankHistory.totalNumber,
        groupRankHistory.participantsNumber,
        SimpleGroupRankDto(groupRankHistory.groupRank),
        groupRankHistory.createdAt,
        groupRankHistory.updatedAt,
    )

}