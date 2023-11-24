package com.better.betterbackend.grouprank.domain

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.study.domain.Study
import jakarta.persistence.*

@Entity
@Table(name = "group_rank")
class GroupRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "score")
    var score: Int = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    var study: Study? = null,

    @OneToMany(mappedBy = "groupRank", cascade = [CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE])
    var groupRankHistoryList: List<GroupRankHistory> = ArrayList(),

): BaseTimeEntity() {

}