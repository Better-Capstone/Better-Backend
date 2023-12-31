package com.better.betterbackend.domain.grouprank.dto

import com.better.betterbackend.domain.grouprankhistory.dto.SimpleGroupRankHistoryDto
import com.better.betterbackend.domain.study.dto.SimpleStudyDto
import com.better.betterbackend.grouprank.domain.GroupRank
import java.time.LocalDateTime

data class GroupRankDto(

    val id: Long,

    val score: Int,

    val study: SimpleStudyDto,

    val groupRankHistoryList: List<SimpleGroupRankHistoryDto>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(groupRank: GroupRank): this(
        groupRank.id!!,
        groupRank.score,
        SimpleStudyDto(groupRank.study!!),
        groupRank.groupRankHistoryList.map { SimpleGroupRankHistoryDto(it) },
        groupRank.createdAt,
        groupRank.updatedAt,
    )

}