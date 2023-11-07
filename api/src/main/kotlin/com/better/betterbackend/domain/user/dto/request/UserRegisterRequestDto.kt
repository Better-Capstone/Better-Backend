package com.better.betterbackend.domain.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UserRegisterRequestDto (

    @field:NotBlank(message = "빈 칸일 수 없습니다")
    @field:Size(min = 1, max = 10, message = "1 ~ 10글자를 입력해야합니다")
    val nickname: String?,

    @field:NotBlank(message = "빈 칸일 수 없습니다")
    val accessToken: String?,

) {

}