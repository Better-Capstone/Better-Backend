package com.better.betterbackend.userrankhistory.domain

import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.study.domain.Study
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class UserRankHistory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val uid: String,

    @ManyToOne
    val userRank: UserRank,

    @ManyToOne
    val study: Study,

    val score: Int,

    val description: String,

    ) {
}