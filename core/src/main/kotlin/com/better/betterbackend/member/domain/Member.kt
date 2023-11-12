package com.better.betterbackend.member.domain

import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Member (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var study: Study? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @OneToMany(mappedBy = "member", cascade = [CascadeType.REMOVE])
    val taskList: List<Task> = ArrayList(),

    var kickCount: Int = 0,

    val memberType: MemberType,

    val notifyTime: LocalDateTime,

) {

}