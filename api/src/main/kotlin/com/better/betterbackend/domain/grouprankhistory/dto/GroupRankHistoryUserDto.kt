package com.better.betterbackend.domain.grouprankhistory.dto

import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.challenge.dto.ChallengeUserDto
import com.better.betterbackend.domain.grouprank.dto.SimpleGroupRankDto
import com.better.betterbackend.domain.taskgroup.dto.SimpleTaskGroupDto
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import java.time.LocalDateTime


class GroupRankHistoryUserDto (
    groupRankHistory: GroupRankHistory,
    val ChallengeList: List<ChallengeDto>,
    val userList: List<UserDto>

): GroupRankHistoryDto(groupRankHistory) {//todo 삭제

    constructor(groupRankHistory:GroupRankHistory) :this(
        groupRankHistory,
//        groupRankHistory.taskGroup.taskList.map { ChallengeUserDto(it.challenge!!,it.member.user) }
        groupRankHistory.taskGroup.taskList.map { ChallengeDto(it.challenge!!) },
        groupRankHistory.taskGroup.taskList.map { UserDto(it.member.user!!) }
    )

}
