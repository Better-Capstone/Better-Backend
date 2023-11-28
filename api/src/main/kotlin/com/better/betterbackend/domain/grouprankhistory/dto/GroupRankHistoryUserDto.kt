package com.better.betterbackend.domain.grouprankhistory.dto

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.challenge.dto.ChallengeUserDto
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.user.domain.User

class GroupRankHistoryUserDto (
    groupRankHistory: GroupRankHistory,
    val challenge: ChallengeUserDto,

){//todo 삭제

    constructor(groupRankHistory:GroupRankHistory,challenge: Challenge, user: User) :this(
        groupRankHistory,
        ChallengeUserDto(challenge,user),
    )

}