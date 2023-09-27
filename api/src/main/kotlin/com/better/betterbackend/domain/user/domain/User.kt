package com.better.betterbackend.domain.user.domain

import com.better.betterbackend.domain.member.domain.Member
import com.better.betterbackend.domain.userrank.domain.UserRank
import com.better.betterbackend.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val nickname: String,

    val name: String,

    val kakaoId: String,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val userRank: UserRank,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val memberList: List<Member>,

): BaseTimeEntity() {
}