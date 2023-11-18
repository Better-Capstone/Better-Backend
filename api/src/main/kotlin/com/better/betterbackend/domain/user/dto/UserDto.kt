package com.better.betterbackend.domain.user.dto

import com.better.betterbackend.domain.member.dto.SimpleMemberDto
import com.better.betterbackend.domain.study.dto.SimpleStudyDto
import com.better.betterbackend.domain.userrank.dto.SimpleUserRankDto
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.user.domain.User
import java.time.LocalDateTime

data class UserDto (

    val id: Long,

    val nickname: String,

    val name: String,

    val userRank: SimpleUserRankDto,

    val memberList: List<SimpleMemberDto>,

    val ownerStudyList: List<SimpleStudyDto>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

){

    constructor(user: User): this(
        user.id!!,
        user.nickname,
        user.name,
        SimpleUserRankDto(user.userRank),
        user.memberList
            .filter { it.memberType != MemberType.WITHDRAW }
            .map {  SimpleMemberDto(it) },
        user.ownedStudyList.map { SimpleStudyDto(it) },
        user.createdAt,
        user.updatedAt,
    )

}