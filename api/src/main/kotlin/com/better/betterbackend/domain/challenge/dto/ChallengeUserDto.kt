package com.better.betterbackend.domain.challenge.dto

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.user.dto.UserNicknameScoreDto
import com.better.betterbackend.user.domain.User

class ChallengeUserDto (

    challenge: Challenge,

    val user: UserNicknameScoreDto

): ChallengeDto(challenge){

    constructor(challenge: Challenge, user: User):this(
        challenge,
        UserNicknameScoreDto(user),
    )

}