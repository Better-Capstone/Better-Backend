package com.better.betterbackend.domain.member.domain

import com.better.betterbackend.domain.grouprank.domain.GroupRank
import com.better.betterbackend.domain.study.domain.Study
import com.better.betterbackend.domain.user.domain.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

@Entity
class Member (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val study: Study,

    @ManyToOne
    val user: User,

    val kickCount: Int,

    val memberType: MemberType,

    val notifyTime: LocalDateTime,

    @OneToMany(mappedBy = "member", cascade = [CascadeType.REMOVE])
    val groupRankList: List<GroupRank>,

) {
}