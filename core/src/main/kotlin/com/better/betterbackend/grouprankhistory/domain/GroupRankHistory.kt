package com.better.betterbackend.grouprankhistory.domain

import com.better.betterbackend.grouprank.domain.GroupRank
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.taskgroup.domain.TaskGroup
import jakarta.persistence.*

@Entity
class GroupRankHistory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val score: Int,

    val description: String,

    val totalNumber: Int,

    val participantsNumber: Int,

    @ManyToOne
    val groupRank: GroupRank,

    @OneToOne
    val taskGroup: TaskGroup,

): BaseTimeEntity() {

}