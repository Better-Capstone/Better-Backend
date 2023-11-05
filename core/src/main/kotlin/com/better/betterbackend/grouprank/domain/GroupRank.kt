package com.better.betterbackend.grouprank.domain

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.study.domain.Study
import jakarta.persistence.*

@Entity
class GroupRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // todo: 삭제 논의 필요
    var numOfLastAttendees: Int = 0,

    var score: Int = 0,

    @OneToOne
    var study: Study? = null,

    @OneToMany(mappedBy = "groupRank", cascade = [CascadeType.REMOVE])
    val groupRankHistoryList: List<GroupRankHistory> = ArrayList(),

) {

}