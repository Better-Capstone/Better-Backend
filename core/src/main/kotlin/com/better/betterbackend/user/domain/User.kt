package com.better.betterbackend.user.domain

import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.model.BaseTimeEntity
import jakarta.persistence.*

@Entity
class User (

    @Id
    val id: Long,

    val nickname: String,

    val name: String,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val userRank: UserRank?,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val memberList: List<Member>,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.REMOVE])
    val ownedStudyList: List<Study>,

    ): BaseTimeEntity() {
}