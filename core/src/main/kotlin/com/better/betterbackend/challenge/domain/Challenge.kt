package com.better.betterbackend.challenge.domain

import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.model.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Challenge(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    val task: Task,

    @Column(name = "description")
    val description: String,

    @Column(name = "image")
    val image: String,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "approve")
    var approve: List<Long> = ArrayList(),

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "reject")
    var reject: List<Long> = ArrayList(),

    ): BaseTimeEntity() {

}