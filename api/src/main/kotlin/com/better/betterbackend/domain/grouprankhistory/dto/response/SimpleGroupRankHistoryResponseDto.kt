package com.better.betterbackend.domain.grouprankhistory.dto.response

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory

class SimpleGroupRankHistoryResponseDto(

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