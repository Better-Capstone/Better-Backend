package com.better.betterbackend.domain.study.dto.response

import com.better.betterbackend.study.domain.CheckDay
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus

class SimpleStudyResponseDto (

    var id: Long? = null,

    val title: String,

    val description: String,

    var status: StudyStatus,

    val period: Period,

    val checkDay: CheckDay,

    val numOfMember: Int,

    val kickCondition: Int,

    val maximumCount: Int,

    val minRank: Int,

) {
    constructor(study: Study) :this(
        study.id,
        study.title,
        study.description,
        study.status,
        study.period,
        study.checkDay,
        study.numOfMember,
        study.kickCondition,
        study.maximumCount,
        study.minRank
    )

}