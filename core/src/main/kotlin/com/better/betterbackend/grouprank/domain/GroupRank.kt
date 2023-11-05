package com.better.betterbackend.grouprank.domain

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import jakarta.persistence.*

@Entity
class GroupRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val numOfLastAttendees: Int,

    val score: Int,

    @OneToMany(mappedBy = "groupRank", cascade = [CascadeType.REMOVE])
    val groupRankHistoryList: List<GroupRankHistory> = ArrayList(),

) {

}