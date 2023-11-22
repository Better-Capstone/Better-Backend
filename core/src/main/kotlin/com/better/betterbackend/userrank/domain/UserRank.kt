package com.better.betterbackend.userrank.domain

import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.user.domain.User
import jakarta.persistence.*

@Entity
class UserRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var score: Int = 4000,

    @OneToOne
    var user: User? = null,

    @OneToMany(mappedBy = "userRank", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var userRankHistoryList: List<UserRankHistory> = ArrayList(),

    ): BaseTimeEntity() {

}