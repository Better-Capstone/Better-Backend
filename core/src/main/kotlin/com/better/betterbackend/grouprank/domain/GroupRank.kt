package com.better.betterbackend.grouprank.domain

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.study.domain.Study
import jakarta.persistence.*

@Entity
class GroupRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val study: Study,

    val numOfLastAttendees: Int,

    val score: Int,

    @OneToMany(mappedBy = "groupRank", cascade = [CascadeType.REMOVE])
    val groupRankHistoryList: List<GroupRankHistory>,

    ) {
}