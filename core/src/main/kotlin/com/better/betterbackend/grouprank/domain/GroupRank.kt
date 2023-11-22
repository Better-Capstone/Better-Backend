package com.better.betterbackend.grouprank.domain

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.study.domain.Study
import jakarta.persistence.*

@Entity
class GroupRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var score: Int = 0,

    @OneToOne
    var study: Study? = null,

    @OneToMany(mappedBy = "groupRank", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var groupRankHistoryList: List<GroupRankHistory> = ArrayList(),

): BaseTimeEntity() {

}