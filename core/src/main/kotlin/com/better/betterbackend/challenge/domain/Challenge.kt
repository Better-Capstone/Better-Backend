package com.better.betterbackend.challenge.domain

import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.model.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Challenge (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    val task: Task,

    val description: String,

    val image: String,

    @ElementCollection
    val approveMember: List<Long> = ArrayList(),

    @ElementCollection
    val rejectMember: List<Long> = ArrayList(),

): BaseTimeEntity() {

}