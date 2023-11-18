package com.better.betterbackend.domain.taskgroup.dto

import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class SimpleTaskGroupDto (

    var id: Long? = null,

    val status: TaskGroupStatus,

    val startDate: LocalDate,

    val endDate: LocalDate,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(taskGroup: TaskGroup): this(
        taskGroup.id!!,
        taskGroup.status,
        taskGroup.startDate,
        taskGroup.endDate,
        taskGroup.createdAt,
        taskGroup.updatedAt,
    )

}