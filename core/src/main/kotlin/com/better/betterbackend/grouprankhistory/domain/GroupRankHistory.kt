package com.better.betterbackend.grouprankhistory.domain

import com.better.betterbackend.grouprank.domain.GroupRank
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.taskgroup.domain.TaskGroup
import jakarta.persistence.*

@Entity
@Table(name = "group_rank_history")
class GroupRankHistory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "score")
    val score: Int,

    @Column(name = "description")
    val description: String,

    @Column(name = "total_number")
    val totalNumber: Int,

    @Column(name = "participants_number")
    val participantsNumber: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_rank_id")
    val groupRank: GroupRank,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_group_id")
    val taskGroup: TaskGroup,

): BaseTimeEntity() {

}