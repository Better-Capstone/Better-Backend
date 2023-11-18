package com.better.betterbackend.domain.task.dto.request

import com.better.betterbackend.global.validation.ValidationGroup
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class TaskRegisterRequestDto (

    @field:NotNull(message = "스터디 아이디는 필수 값 입니다", groups = [ValidationGroup.NotNullGroup::class])
    @field:Min(value = 0, message = "0 이상의 값 이어야 합니다", groups = [ValidationGroup.MinMaxGroup::class])
    val studyId : Long,

    @field:NotBlank(message = "태스크 제목은 필수 값 입니다", groups = [ValidationGroup.NotNullGroup::class])
    @field:Size(min = 1, max = 20, message = "최소 1글자, 최대 20글자의 문자열 이어야 합니다", groups = [ValidationGroup.SizeGroup::class])
    val title : String,

){

}