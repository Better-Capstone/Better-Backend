package com.better.betterbackend.user.domain

import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.model.BaseTimeEntity
import jakarta.persistence.*

@Entity
class User (

    @Id
    var id: Long? = null,

    val nickname: String,

    val name: String,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var userRank: UserRank?,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val memberList: List<Member>,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.REMOVE])
    val ownedStudyList: List<Study>,

): BaseTimeEntity() {

    // todo: 생성자에 들어가는 UserRank 수정 필요
    constructor(id: Long, nickname: String, name: String ,userRank : UserRank) : this(id, nickname, name, userRank, emptyList(), emptyList())

}