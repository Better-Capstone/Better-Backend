package com.better.betterbackend.domain.grouprankhistory.dto

import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.challenge.dto.ChallengeUserDto
import com.better.betterbackend.domain.grouprank.dto.SimpleGroupRankDto
import com.better.betterbackend.domain.taskgroup.dto.SimpleTaskGroupDto
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import java.time.LocalDateTime

open class GroupRankHistoryDto (

    val id: Long,

    val score: Int,

    val description: String,

    val totalNumber: Int,

    val participantsNumber: Number,

    val groupRank: SimpleGroupRankDto,

    val taskGroup: SimpleTaskGroupDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,


    ) {

    constructor(groupRankHistory: GroupRankHistory): this(
        groupRankHistory.id!!,
        groupRankHistory.score,
        groupRankHistory.description,
        groupRankHistory.totalNumber,
        groupRankHistory.participantsNumber,
        SimpleGroupRankDto(groupRankHistory.groupRank),
        SimpleTaskGroupDto(groupRankHistory.taskGroup),
        groupRankHistory.createdAt,
        groupRankHistory.updatedAt,



    )

}