package com.better.betterbackend.task.domain

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class Task (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "title")
    val title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_group_id")
    val taskGroup: TaskGroup,

    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.MERGE]
    )
    @JoinColumn(name = "challenge_id")
    var challenge: Challenge? = null,

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "task",
        cascade = [CascadeType.REMOVE]
    )
    var userRankHistoryList: List<UserRankHistory> = ArrayList()

): BaseTimeEntity() {

}