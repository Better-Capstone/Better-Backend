package com.better.betterbackend.domain.study.domain

import com.better.betterbackend.domain.category.domain.Category
import com.better.betterbackend.domain.grouprank.domain.GroupRank
import com.better.betterbackend.domain.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.domain.member.domain.Member
import com.better.betterbackend.domain.task.domain.Task
import com.better.betterbackend.domain.userrankhistory.domain.UserRankHistory
import com.better.betterbackend.global.common.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Study (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val ownerId: String,

    @ManyToOne
    val category: Category,

    val title: String,

    val description: String,

    val period: Period,

    val checkDay: CheckDay,

    val numOfMember: Int,

    val kickCondition: Int,

    val maximumCount: Int,

    val minRank: Int,

    @OneToMany(mappedBy = "study", cascade = [CascadeType.REMOVE])
    val memberList: List<Member>,

    @OneToMany(mappedBy = "study", cascade = [CascadeType.REMOVE])
    val taskList: List<Task>,

    @OneToMany(mappedBy = "study", cascade = [CascadeType.REMOVE])
    val userRankHistoryList: List<UserRankHistory>,

    @OneToMany(mappedBy = "study", cascade = [CascadeType.REMOVE])
    val groupRankList: List<GroupRank>,

    @OneToMany(mappedBy = "study", cascade = [CascadeType.REMOVE])
    val groupRankHistoryList: List<GroupRankHistory>,

): BaseTimeEntity() {
}