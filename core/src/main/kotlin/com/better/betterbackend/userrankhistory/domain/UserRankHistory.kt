package com.better.betterbackend.userrankhistory.domain

import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.study.domain.Study
import jakarta.persistence.*

@Entity
class UserRankHistory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val uid: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    val userRank: UserRank,

    @ManyToOne(fetch = FetchType.LAZY)
    val study: Study,

    val score: Int,

    val description: String,

): BaseTimeEntity() {

}