package com.better.betterbackend.domain.study.dto.request

import com.better.betterbackend.study.domain.CheckDay
import com.better.betterbackend.study.domain.Period
import jakarta.validation.constraints.NotNull

class StudyCreateRequestDto(

    val categoryId: Long,

    @field:NotNull(message = "빈 칸일 수 없습니다")
    var title: String?,

    @field:NotNull(message = "빈 칸일 수 없습니다")
    var description: String?,

    val checkDay: CheckDay,

    val kickCondition: Int, 

    val maximumCount: Int,

    val minRank: Int,

    val period: Period,

) {

}
