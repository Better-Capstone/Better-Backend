package com.better.betterbackend.domain.user.domain

import com.better.betterbackend.domain.member.domain.Member
import com.better.betterbackend.domain.study.domain.Study
import com.better.betterbackend.domain.userrank.domain.UserRank
import com.better.betterbackend.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val nickname: String,

    val name: String,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val userRank: UserRank,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val memberList: List<Member>,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.REMOVE])
    val ownedStudyList: List<Study>,

): BaseTimeEntity() {
}