package com.better.betterbackend.study.domain

import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.user.domain.User
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import com.better.betterbackend.grouprank.domain.GroupRank
import com.better.betterbackend.model.BaseTimeEntity
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

    @ManyToOne
    val owner: User,

    @ManyToOne
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
}