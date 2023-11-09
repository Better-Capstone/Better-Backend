package com.better.betterbackend.domain.study.dto.response

import com.better.betterbackend.domain.category.dto.response.SimpleCategoryResponseDto
import com.better.betterbackend.domain.grouprank.dto.response.SimpleGroupRankResponseDto
import com.better.betterbackend.domain.member.dto.response.SimpleMemberResponseDto
import com.better.betterbackend.domain.task.dto.response.SimpleTaskResponseDto
import com.better.betterbackend.domain.user.dto.response.SimpleUserResponseDto
import com.better.betterbackend.domain.userRankHistory.dto.response.SimpleUserRankHistoryResponseDto
import com.better.betterbackend.study.domain.CheckDay
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus

class StudyResponseDto(

    val id: Long,

    val owner: SimpleUserResponseDto,

    val category: SimpleCategoryResponseDto,

    val title: String,

    val description: String,

    val status: StudyStatus,

    val period: Period,

    val checkDay: CheckDay,

    val numOfMember: Int,

    val kickCondition: Int,

    val maximumCount: Int,

    val minRank: Int,

    val memberList: List<SimpleMemberResponseDto>,

    val taskList: List<SimpleTaskResponseDto>,

    val userRankHistoryList: List<SimpleUserRankHistoryResponseDto>,

    val groupRank: SimpleGroupRankResponseDto

) {

    constructor(study: Study): this(
        study.id!!,
        SimpleUserResponseDto(study.owner),
        SimpleCategoryResponseDto(study.category),
        study.title,
        study.description,
        study.status,
        study.period!!,
        study.checkDay!!,
        study.numOfMember,
        study.kickCondition,
        study.maximumCount,
        study.minRank,
        study.memberList.map{ SimpleMemberResponseDto(it) },
        study.taskList.map{ SimpleTaskResponseDto(it) },
        study.userRankHistoryList.map { SimpleUserRankHistoryResponseDto(it) },
        SimpleGroupRankResponseDto(study.groupRank),
    )

}