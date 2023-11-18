package com.better.betterbackend.domain.task.dto

import com.better.betterbackend.task.domain.Task
import java.time.LocalDateTime

data class SimpleTaskDto(

    val id: Long,

    val title: String,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(task: Task): this(
        task.id!!,
        task.title,
        task.createdAt,
        task.updatedAt,
    )

}