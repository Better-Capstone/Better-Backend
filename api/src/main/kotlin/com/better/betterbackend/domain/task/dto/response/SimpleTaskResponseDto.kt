package com.better.betterbackend.domain.task.dto.response

import com.better.betterbackend.task.domain.Task
import java.time.LocalDateTime

class SimpleTaskResponseDto(

    val id: Long,

    val title: String,

    val deadline: LocalDateTime,

) {

    constructor(task: Task): this(
        task.id!!,
        task.title,
        task.deadline
    )

}