package com.better.betterbackend.domain.study.dto.request

import com.better.betterbackend.global.validation.ValidationGroup.*
import com.better.betterbackend.study.domain.CheckDay
import com.better.betterbackend.study.domain.Period
import jakarta.validation.constraints.*


data class StudyCreateRequestDto(

    @field:NotNull(message = "카테고리는 필수 값 입니다", groups = [NotNullGroup::class])
    @field:Min(value = 0, message = "0 이상의 값 이어야 합니다", groups = [MinMaxGroup::class])
    val categoryId: Long?,

    @field:NotBlank(message = "스터디 제목은 필수 값 입니다", groups = [NotNullGroup::class])
    @field:Size(min = 1, max = 20, message = "최소 1글자, 최대 20글자의 문자열 이어야 합니다", groups = [SizeGroup::class])
    val title: String?,

    @field:NotBlank(message = "스터디 설명은 필수 값 입니다", groups = [NotNullGroup::class])
    val description: String?,

    @field:NotNull(message = "유효하지 않은 태스크 검증 요일 입니다", groups = [NotNullGroup::class])
    val checkDay: CheckDay?,

    @field:NotNull(message = "퇴출 조건은 필수 값 입니다", groups = [NotNullGroup::class])
    @field:Min(value = 1, message = "1 이상 5 이하의 값 이어야 합니다", groups = [MinMaxGroup::class])
    @field:Max(value = 5, message = "1 이상 5 이하의 값 이어야 합니다", groups = [MinMaxGroup::class])
    val kickCondition: Int?,

    @field:NotNull(message = "최대 인원은 필수 값 입니다", groups = [NotNullGroup::class])
    @field:Min(value = 1, message = "1 이상 5 이하의 값 이어야 합니다", groups = [MinMaxGroup::class])
    @field:Max(value = 50, message = "1 이상 50 이하의 값 이어야 합니다", groups = [MinMaxGroup::class])
    val maximumCount: Int?,

    @field:NotNull(message = "최소 랭크는 필수 값 입니다", groups = [NotNullGroup::class])
    @field:Min(value = 0, message = "0 이상 10000 이하의 값 이어야 합니다", groups = [MinMaxGroup::class])
    @field:Max(value = 10000, message = "0 이상 10000 이하의 값 이어야 합니다", groups = [MinMaxGroup::class])
    val minRank: Int?,

    @field:NotNull(message = "유효하지 않은 태스크 주기 입니다", groups = [NotNullGroup::class])
    val period: Period?,

) {

}
