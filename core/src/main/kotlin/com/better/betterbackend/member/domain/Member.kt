package com.better.betterbackend.member.domain

import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Member (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    var study: Study? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "member",
        cascade = [CascadeType.REMOVE]
    )
    val taskList: List<Task> = ArrayList(),

    @Column(name = "kick_count")
    var kickCount: Int = 0,

    @Column(name = "member_type")
    var memberType: MemberType,

    @Column(name = "notify_time")
    val notifyTime: LocalDateTime,

): BaseTimeEntity() {

}