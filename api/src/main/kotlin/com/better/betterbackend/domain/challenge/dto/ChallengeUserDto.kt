package com.better.betterbackend.domain.challenge.dto

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.user.domain.User


class ChallengeUserDto (
    val challenge: Challenge,
    val user: UserDto
) :ChallengeDto(challenge){
    constructor(challenge: Challenge,user:User):this(
        challenge,
        UserDto(user),
    )
}