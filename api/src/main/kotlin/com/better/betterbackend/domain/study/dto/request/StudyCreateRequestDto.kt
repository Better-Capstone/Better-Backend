package com.better.betterbackend.domain.study.dto.request

import com.better.betterbackend.study.domain.CheckDay
import com.better.betterbackend.study.domain.Period

class StudyCreateRequestDto(

    val categoryId: Long,

    val title: String,

    val description: String,

    val checkDay: CheckDay,

    val kickCondition: Int, 

    val maximumCount: Int,

    val minRank: Int,

    val period: Period,

) {

}
