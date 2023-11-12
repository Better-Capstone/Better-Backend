package com.better.betterbackend.domain.user.dto.request

import com.better.betterbackend.global.validation.ValidationGroup.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UserRegisterRequestDto (

    @field:NotBlank(message = "닉네임은 필수 값 입니다", groups = [NotNullGroup::class])
    @field:Size(min = 1, max = 10, message = "최소 1글자, 최대 10글자의 문자열 이어야 합니다", groups = [SizeGroup::class])
    val nickname: String?,

    @field:NotBlank(message = "Access Token은 필수 값 입니다", groups = [NotNullGroup::class])
    val accessToken: String?,

) {

}