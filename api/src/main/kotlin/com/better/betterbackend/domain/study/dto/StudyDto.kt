package com.better.betterbackend.domain.study.dto

import com.better.betterbackend.domain.category.dto.SimpleCategoryDto
import com.better.betterbackend.domain.grouprank.dto.SimpleGroupRankDto
import com.better.betterbackend.domain.member.dto.SimpleMemberDto
import com.better.betterbackend.domain.task.dto.SimpleTaskDto
import com.better.betterbackend.domain.taskgroup.dto.SimpleTaskGroupDto
import com.better.betterbackend.domain.user.dto.SimpleUserDto
import com.better.betterbackend.domain.userRankHistory.dto.SimpleUserRankHistoryDto
import com.better.betterbackend.study.domain.CheckDay
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus
import java.time.LocalDateTime

data class StudyDto(

    val id: Long,

    val owner: SimpleUserDto,

    val category: SimpleCategoryDto,

    val title: String,

    val description: String,

    val status: StudyStatus,

    val period: Period,

    val checkDay: CheckDay,

    val numOfMember: Int,

    val kickCondition: Int,

    val maximumCount: Int,

    val minRank: Int,

    val memberList: List<SimpleMemberDto>,

    val taskGroupList: List<SimpleTaskGroupDto>,

    val userRankHistoryList: List<SimpleUserRankHistoryDto>,

    val groupRank: SimpleGroupRankDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(study: Study): this(
        study.id!!,
        SimpleUserDto(study.owner),
        SimpleCategoryDto(study.category),
        study.title,
        study.description,
        study.status,
        study.period,
        study.checkDay,
        study.numOfMember,
        study.kickCondition,
        study.maximumCount,
        study.minRank,
        study.memberList.map{ SimpleMemberDto(it) },
        study.taskGroupList.map{ SimpleTaskGroupDto(it) },
        study.userRankHistoryList.map { SimpleUserRankHistoryDto(it) },
        SimpleGroupRankDto(study.groupRank),
        study.createdAt,
        study.updatedAt,
    )

}