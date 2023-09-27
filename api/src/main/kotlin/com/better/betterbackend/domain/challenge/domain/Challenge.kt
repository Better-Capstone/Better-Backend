package com.better.betterbackend.domain.challenge.domain

import com.better.betterbackend.domain.task.domain.Task
import com.better.betterbackend.global.common.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class Challenge (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    val task: Task,

    val description: String,

    val image: String,

    val approvedMember: Double,

): BaseTimeEntity() {
}