package com.better.betterbackend.domain.grouprank.dto

import com.better.betterbackend.grouprank.domain.GroupRank
import java.time.LocalDateTime

data class SimpleGroupRankDto(

    val id: Long,

    val score: Int,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(groupRank: GroupRank): this(
        groupRank.id!!,
        groupRank.score,
        groupRank.createdAt,
        groupRank.updatedAt,
    )

}