package com.better.betterbackend.domain.grouprank.dto.response

import com.better.betterbackend.grouprank.domain.GroupRank

class SimpleGroupRankResponseDto(

    val id: Long,

    val score: Int,

) {

    constructor(groupRank: GroupRank): this(
        groupRank.id!!,
        groupRank.score
    )

}