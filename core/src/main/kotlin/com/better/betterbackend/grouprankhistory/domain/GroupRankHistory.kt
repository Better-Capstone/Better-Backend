package com.better.betterbackend.grouprankhistory.domain

import com.better.betterbackend.grouprank.domain.GroupRank
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