package com.better.betterbackend.domain.member.dto

import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.user.domain.User

import java.time.LocalDateTime

class SimpleMemberResponseDto (
    var id: Long? = null,

    val study: Study,

    val taskList: List<Task>,

    val kickCount: Int,

    val memberType: MemberType,

    val notifyTime: LocalDateTime,
){
    constructor(member: Member): this(member.id, member.study,member.taskList,
        member.kickCount,member.memberType,member.notifyTime)
}