package com.better.betterbackend.domain.challenge.dto.request

import com.better.betterbackend.global.validation.ValidationGroup
import jakarta.validation.constraints.NotNull

data class ChallengeApproveRequestDto (

    @field:NotNull(message = "승인 여부는 필수 값 입니다", groups = [ValidationGroup.NotNullGroup::class])
    val approved : Boolean

){

}