package com.better.betterbackend.domain.challenge.dto.request

import com.better.betterbackend.global.validation.ValidationGroup
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ChallengeRegisterRequestDto (

    @field:NotBlank(message = "챌린지 제목은 필수 값 입니다", groups = [ValidationGroup.NotNullGroup::class])
    @field:Size(min = 1, max = 20, message = "최소 1글자, 최대 20글자의 문자열 이어야 합니다", groups = [ValidationGroup.SizeGroup::class])
    val title : String,

    @field:NotBlank(message = "챌린지 이미지는 필수 값 입니다", groups = [ValidationGroup.NotNullGroup::class])
    val image : String,

    @field:NotBlank(message = "챌린지 설명은 필수 값 입니다", groups = [ValidationGroup.NotNullGroup::class])
    val description : String,

){

}