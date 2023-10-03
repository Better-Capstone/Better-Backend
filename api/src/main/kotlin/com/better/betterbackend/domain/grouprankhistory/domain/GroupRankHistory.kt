package com.better.betterbackend.domain.grouprankhistory.domain

import com.better.betterbackend.domain.grouprank.domain.GroupRank
import jakarta.persistence.*

@Entity
class GroupRankHistory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val percent: Double,

    @ManyToOne
    val groupRank: GroupRank,

) {
}