package com.better.betterbackend.domain.userrank.dto

import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.userrankhistory.domain.UserRankHistory

class UserRankResponseDto (

    var id: Long? = null,

    val score: Int,

    val userRankHistoryList: List<UserRankHistory>,

) {
    constructor(userRank: UserRank) : this(userRank.id, userRank.score, userRank.userRankHistoryList)

}