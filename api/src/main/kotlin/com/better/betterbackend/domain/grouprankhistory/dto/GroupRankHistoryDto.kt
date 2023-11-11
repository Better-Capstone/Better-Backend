package com.better.betterbackend.domain.grouprankhistory.dto

import com.better.betterbackend.domain.grouprank.dto.SimpleGroupRankDto
import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory

data class GroupRankHistoryDto (

    val id: Long,

    val score: Int,

    val totalNumber: Int,

    val participantsNumber: Number,

    val groupRank: SimpleGroupRankDto

) {

    constructor(groupRankHistory: GroupRankHistory): this(
        groupRankHistory.id!!,
        groupRankHistory.score,
        groupRankHistory.totalNumber,
        groupRankHistory.participantsNumber,
        SimpleGroupRankDto(groupRankHistory.groupRank),
    )

}