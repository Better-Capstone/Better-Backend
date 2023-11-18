package com.better.betterbackend.domain.grouprankhistory.dto

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import java.time.LocalDateTime

data class SimpleGroupRankHistoryDto(

    val id: Long,

    val score: Int,

    val totalNumber: Int,

    val participantsNumber: Int,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(groupRankHistory: GroupRankHistory): this(
        groupRankHistory.id!!,
        groupRankHistory.score,
        groupRankHistory.totalNumber,
        groupRankHistory.participantsNumber,
        groupRankHistory.createdAt,
        groupRankHistory.updatedAt,
    )

}