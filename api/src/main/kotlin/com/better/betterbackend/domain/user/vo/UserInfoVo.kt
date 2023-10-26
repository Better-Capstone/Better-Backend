package com.better.betterbackend.domain.user.vo

import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoVo (
    var id:Long,
    @JsonProperty("connected_at") var connectedAt:String,
    @JsonProperty("kakao_account") var kakaoAccount: KakaoAccount
)

data class KakaoAccount(
    @JsonProperty("profile_nickname_needs_agreement") var profileNicknameNeedsAgreement:Boolean,
    var profile: Profile
)

data class Profile(
    var nickname:String
)