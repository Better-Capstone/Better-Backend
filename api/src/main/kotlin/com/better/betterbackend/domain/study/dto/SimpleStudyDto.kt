package com.better.betterbackend.domain.study.dto

import com.better.betterbackend.study.domain.CheckDay
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus
import java.time.LocalDateTime

data class SimpleStudyDto (

    val id: Long,

    val title: String,

    val description: String,

    val status: StudyStatus,

    val period: Period,

    val checkDay: CheckDay,

    val numOfMember: Int,

    val kickCondition: Int,

    val maximumCount: Int,

    val minRank: Int,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(study: Study) :this(
        study.id!!,
        study.title,
        study.description,
        study.status,
        study.period,
        study.checkDay,
        study.numOfMember,
        study.kickCondition,
        study.maximumCount,
        study.minRank,
        study.createdAt,
        study.updatedAt,
    )

}