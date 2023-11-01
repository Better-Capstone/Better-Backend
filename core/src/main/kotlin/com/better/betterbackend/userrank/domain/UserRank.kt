package com.better.betterbackend.userrank.domain

import com.better.betterbackend.user.domain.User
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import com.better.betterbackend.model.BaseTimeEntity
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