package com.better.betterbackend.domain.member.dto.response

import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.domain.Study

import java.time.LocalDateTime

class SimpleMemberResponseDto (

    val id: Long,

    val kickCount: Int,

    val memberType: MemberType,

    val notifyTime: LocalDateTime,

){
    constructor(member: Member): this(
        member.id!!,
        member.kickCount,
        member.memberType,
        member.notifyTime
    )

}