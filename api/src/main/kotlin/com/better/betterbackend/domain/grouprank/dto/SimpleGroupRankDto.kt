package com.better.betterbackend.domain.grouprank.dto

import com.better.betterbackend.grouprank.domain.GroupRank

data class SimpleGroupRankDto(

    val id: Long,

    val score: Int,

) {

    constructor(groupRank: GroupRank): this(
        groupRank.id!!,
        groupRank.score
    )

}