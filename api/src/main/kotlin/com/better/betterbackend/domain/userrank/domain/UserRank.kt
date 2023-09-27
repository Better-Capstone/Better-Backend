package com.better.betterbackend.domain.userrank.domain

import com.better.betterbackend.domain.user.domain.User
import com.better.betterbackend.domain.userrankhistory.domain.UserRankHistory
import com.better.betterbackend.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
class UserRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val score: Int,

    @OneToOne
    val user: User,

    @OneToMany(mappedBy = "userRank", cascade = [CascadeType.REMOVE])
    val userRankHistoryList: List<UserRankHistory>,

): BaseTimeEntity() {
}