package com.better.betterbackend.domain.grouprankhistory.dto

import com.better.betterbackend.domain.challenge.dto.ChallengeUserDto
import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory


class GroupRankHistoryChallengeDto (

    groupRankHistory: GroupRankHistory,

    val challengeList: List<ChallengeUserDto>,

): GroupRankHistoryDto(groupRankHistory) {

    constructor(groupRankHistory: GroupRankHistory): this(
        groupRankHistory,
        groupRankHistory.taskGroup.taskList
            .filter { it.challenge != null }
            .map { ChallengeUserDto(it.challenge!!, it.member.user) },
    )

}
