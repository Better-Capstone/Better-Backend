package com.better.betterbackend.task.domain

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Task (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val taskGroup: TaskGroup,

    @ManyToOne
    val member: Member,

    val title: String,

    @OneToOne(cascade = [CascadeType.PERSIST])
    var challenge: Challenge?,

): BaseTimeEntity() {

}