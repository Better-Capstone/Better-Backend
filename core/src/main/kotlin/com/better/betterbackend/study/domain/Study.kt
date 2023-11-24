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
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "status")
    var status: StudyStatus = StudyStatus.INPROGRESS,

    @Column(name = "period")
    val period: Period,

    @Column(name = "check_day")
    val checkDay: CheckDay,

    @Column(name = "num_of_member")
    var numOfMember: Int = 0,

    @Column(name = "kick_condition")
    val kickCondition: Int,

    @Column(name = "maximum_count")
    val maximumCount: Int,//인원수

    @Column(name = "min_rank")
    val minRank: Int,

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "study",
        cascade = [CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE]
    )
    var memberList: List<Member>,

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "study",
        cascade = [CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE]
    )
    var taskGroupList: List<TaskGroup>,

    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE]
    )
    @JoinColumn(name = "group_rank_id")
    val groupRank: GroupRank,

): BaseTimeEntity() {

}