package com.better.betterbackend.study.domain

import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.user.domain.User
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import com.better.betterbackend.grouprank.domain.GroupRank
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.taskgroup.domain.TaskGroup
import jakarta.persistence.*

@Entity
class Study (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val owner: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val category: Category,

    val title: String,

    val description: String,

    var status: StudyStatus = StudyStatus.INPROGRESS,

    val period: Period,

    val checkDay: CheckDay,

    var numOfMember: Int = 0,

    val kickCondition: Int,

    val maximumCount: Int,//인원수

    val minRank: Int,

    @OneToMany(mappedBy = "study", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var memberList: List<Member>,

    @OneToMany(mappedBy = "study", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var taskGroupList: List<TaskGroup>,

    @OneToMany(mappedBy = "study", cascade = [CascadeType.REMOVE])
    val userRankHistoryList: List<UserRankHistory> = ArrayList(),

    @OneToOne(cascade = [CascadeType.PERSIST])
    val groupRank: GroupRank,

): BaseTimeEntity() {

}