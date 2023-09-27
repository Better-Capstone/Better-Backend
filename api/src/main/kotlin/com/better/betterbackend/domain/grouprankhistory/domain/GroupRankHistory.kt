package com.better.betterbackend.domain.grouprankhistory.domain

import com.better.betterbackend.domain.study.domain.Study
import jakarta.persistence.*

@Entity
class GroupRankHistory (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val study: Study,

    val percent: Double,

) {
}