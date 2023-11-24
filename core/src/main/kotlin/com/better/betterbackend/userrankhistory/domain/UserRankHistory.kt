package com.better.betterbackend.userrankhistory.domain

import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.task.domain.Task
import jakarta.persistence.*

@Entity
@Table(name = "user_rank_history")
class UserRankHistory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "score")
    val score: Int,

    @Column(name = "description")
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_rank_id")
    val userRank: UserRank,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    val task: Task?,

): BaseTimeEntity() {

}