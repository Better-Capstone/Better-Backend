package com.better.betterbackend.domain.grouprank.domain

import com.better.betterbackend.domain.member.domain.Member
import com.better.betterbackend.domain.study.domain.Study
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class GroupRank (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val study: Study,

    @ManyToOne
    val member: Member,

    val numOfLastAttendees: Int,

    val score: Int,

) {
}