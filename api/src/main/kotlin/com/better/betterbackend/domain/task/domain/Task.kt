package com.better.betterbackend.domain.task.domain

import com.better.betterbackend.domain.challenge.domain.Challenge
import com.better.betterbackend.domain.study.domain.Study
import com.better.betterbackend.global.common.BaseTimeEntity
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

    val title: String,

    val deadline: LocalDateTime,

    @OneToOne
    val challenge: Challenge,

): BaseTimeEntity() {
}