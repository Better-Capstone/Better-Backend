package com.better.betterbackend.domain.grouprankhistory.dto

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory

data class SimpleGroupRankHistoryDto(

    val id: Long,

    val score: Int,

    val totalNumber: Int,

    val participantsNumber: Int,

) {

    constructor(groupRankHistory: GroupRankHistory): this(
        groupRankHistory.id!!,
        groupRankHistory.score,
        groupRankHistory.totalNumber,
        groupRankHistory.participantsNumber,
    )

}