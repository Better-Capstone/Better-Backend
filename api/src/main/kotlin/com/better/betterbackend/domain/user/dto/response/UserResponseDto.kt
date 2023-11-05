package com.better.betterbackend.domain.user.dto.response

import com.better.betterbackend.domain.member.dto.response.SimpleMemberResponseDto
import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto
import com.better.betterbackend.domain.userrank.dto.SimpleUserRankResponseDto
import com.better.betterbackend.user.domain.User
import java.time.LocalDateTime

class UserResponseDto (

    val id: Long,

    val nickname: String,

    val name: String,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

    val userRank: SimpleUserRankResponseDto,

    val memberList: List<SimpleMemberResponseDto>,

    val ownerStudyList: List<SimpleStudyResponseDto>

){

    constructor(user: User): this(
        user.id!!,
        user.nickname,
        user.name,
        user.createdAt,
        user.updatedAt,
        SimpleUserRankResponseDto(user.userRank),
        user.memberList.map {  SimpleMemberResponseDto(it) },
        user.ownedStudyList.map { SimpleStudyResponseDto(it) }
    )

}