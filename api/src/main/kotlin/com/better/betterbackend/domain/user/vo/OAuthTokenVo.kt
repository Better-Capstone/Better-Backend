package com.better.betterbackend.domain.user.vo


data class OAuthTokenVo (
    var access_token : String? = null,
    var token_type : String? = null,
    var refresh_token : String? = null,
    var expires_in : Int = 0,
    var scope : String? = null,
    var refresh_token_expires_in : Int = 0,
){
}