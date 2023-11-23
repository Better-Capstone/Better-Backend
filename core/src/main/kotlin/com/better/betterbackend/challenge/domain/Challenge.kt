package com.better.betterbackend.challenge.domain

import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.model.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Challenge(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    val task: Task,

    val description: String,

    val image: String,

    @ElementCollection
    var approve: List<Long> = ArrayList(),

    @ElementCollection
    var reject: List<Long> = ArrayList(),

    ): BaseTimeEntity() {

}