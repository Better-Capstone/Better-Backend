package com.better.betterbackend.domain.grouprank.domain

import com.better.betterbackend.domain.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.domain.study.domain.Study
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