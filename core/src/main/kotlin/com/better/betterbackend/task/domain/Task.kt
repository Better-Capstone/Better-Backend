package com.better.betterbackend.task.domain

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.study.domain.StudyStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Task (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val study: Study,

    @ManyToOne
    val member: Member,

    val title: String,

    val deadline: LocalDateTime,

    @OneToOne(cascade = [CascadeType.PERSIST])
    var challenge: Challenge?,

    var status: TaskStatus = TaskStatus.INPROGRESS,

    ): BaseTimeEntity() {
//    constructor(
//        study: Study,
//        title: String,
//        deadline: LocalDateTime,
//        ): this(null, study, title, deadline,)

}