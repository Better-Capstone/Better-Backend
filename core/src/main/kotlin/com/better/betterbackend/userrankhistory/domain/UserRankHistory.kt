package com.better.betterbackend.userrankhistory.domain

import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.task.domain.Task
import jakarta.persistence.*

@Entity
class UserRankHistory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val score: Int,

    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val userRank: UserRank,

    @OneToOne
    val task: Task,

): BaseTimeEntity() {

}