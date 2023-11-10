package com.better.betterbackend.domain.grouprankhistory.dto.response

import com.better.betterbackend.domain.grouprank.dto.response.SimpleGroupRankResponseDto
import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory

class GroupRankHistoryResponseDto (

    val id: Long,

    val score: Int,

    val totalNumber: Int,

    val participantsNumber: Number,

    val groupRank: SimpleGroupRankResponseDto

) {

    constructor(groupRankHistory: GroupRankHistory): this(
        groupRankHistory.id!!,
        groupRankHistory.score,
        groupRankHistory.totalNumber,
        groupRankHistory.participantsNumber,
        SimpleGroupRankResponseDto(groupRankHistory.groupRank),
    )

}