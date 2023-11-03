package com.better.betterbackend.task.domain

import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.model.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
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

    @OneToOne
    val challenge: Challenge,

): BaseTimeEntity() {

}