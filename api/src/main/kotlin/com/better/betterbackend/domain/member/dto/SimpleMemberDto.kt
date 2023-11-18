package com.better.betterbackend.domain.member.dto

import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.member.domain.MemberType

import java.time.LocalDateTime

data class SimpleMemberDto (

    val id: Long,

    val kickCount: Int,

    val memberType: MemberType,

    val notifyTime: LocalDateTime,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

){

    constructor(member: Member): this(
        member.id!!,
        member.kickCount,
        member.memberType,
        member.notifyTime,
        member.createdAt,
        member.updatedAt,
    )

}