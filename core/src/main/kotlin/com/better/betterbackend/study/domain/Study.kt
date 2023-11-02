package com.better.betterbackend.study.domain

import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.user.domain.User
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import com.better.betterbackend.grouprank.domain.GroupRank
import com.better.betterbackend.model.BaseTimeEntity
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

    var status: StudyStatus,

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

): BaseTimeEntity() {

    constructor(
        user: User,
        category: Category,
        title: String,
        description: String,
        checkDay: CheckDay,
        kickCondition: Int,
        maximumCount: Int,
        minRank: Int,
        period: Period
    ): this(
        // todo: member 추가
        null, user, category, title, description, StudyStatus.INPROGRESS, period, checkDay, 0, kickCondition, maximumCount, minRank, emptyList(), emptyList(), emptyList(), emptyList()
    )

}