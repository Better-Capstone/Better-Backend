package com.better.betterbackend.domain.task.dto

import com.better.betterbackend.domain.challenge.dto.SimpleChallengeDto
import com.better.betterbackend.domain.member.dto.SimpleMemberDto
import com.better.betterbackend.domain.study.dto.SimpleStudyDto
import com.better.betterbackend.domain.taskgroup.dto.SimpleTaskGroupDto
import com.better.betterbackend.domain.userRankHistory.dto.SimpleUserRankHistoryDto
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import java.time.LocalDateTime

data class TaskDto(

    var id: Long,

    val title: String,

    val member: SimpleMemberDto,

    val taskGroup: SimpleTaskGroupDto,

    val challenge: SimpleChallengeDto?,

    val userRankHistory: List<SimpleUserRankHistoryDto>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

){

    constructor(task: Task) : this(
        task.id!!,
        task.title,
        SimpleMemberDto(task.member),
        SimpleTaskGroupDto(task.taskGroup),
        if (task.challenge != null) SimpleChallengeDto(task.challenge!!) else null,
        task.userRankHistoryList.map { SimpleUserRankHistoryDto(it) },
        task.createdAt,
        task.updatedAt,
    )
}
