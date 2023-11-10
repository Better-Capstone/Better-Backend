package com.better.betterbackend.member.domain

enum class MemberType (

    val type: String,

) {

    OWNER("Owner"),
    MEMBER("Member"),
    WITHDRAW("Withdraw")
    ;

}