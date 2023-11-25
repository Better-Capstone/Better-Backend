package com.better.betterbackend.domain.task.dto.response

import com.better.betterbackend.domain.study.dto.StudyIdAndTitleDto
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task

class TaskWithStudyResponseDto(

    task: Task,

    val study: StudyIdAndTitleDto,

): TaskDto(task) {

    constructor(task: Task, study: Study): this(
        task,
        StudyIdAndTitleDto(study)
    )

}