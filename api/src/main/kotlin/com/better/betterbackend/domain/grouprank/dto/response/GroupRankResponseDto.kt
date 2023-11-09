package com.better.betterbackend.domain.grouprank.dto.response

import com.better.betterbackend.domain.grouprankhistory.dto.response.SimpleGroupRankHistoryResponseDto
import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto
import com.better.betterbackend.grouprank.domain.GroupRank

class GroupRankResponseDto(

    val id: Long,

    val score: Int,

    val study: SimpleStudyResponseDto,

    val groupRankHistoryList: List<SimpleGroupRankHistoryResponseDto>,

    ) {

    constructor(groupRank: GroupRank): this(
        groupRank.id!!,
        groupRank.score,
        SimpleStudyResponseDto(groupRank.study!!),
        groupRank.groupRankHistoryList.map { SimpleGroupRankHistoryResponseDto(it) }
    )

}