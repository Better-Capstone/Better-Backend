package com.better.betterbackend.domain.userrankhistory.domain

import com.better.betterbackend.domain.study.domain.Study
import com.better.betterbackend.domain.userrank.domain.UserRank
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