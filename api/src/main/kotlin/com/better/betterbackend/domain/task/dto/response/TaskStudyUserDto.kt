package com.better.betterbackend.domain.task.dto.response

import com.better.betterbackend.domain.study.dto.StudyIdAndTitleDto
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.user.dto.SimpleUserTasksDto
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.domain.userrank.dto.SimpleUserRankDto
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.user.domain.User

class TaskStudyUserDto(


    task: Task,
    val study: StudyIdAndTitleDto,

    val user: SimpleUserTasksDto,

    ) : TaskDto(task) {
    constructor(task: Task, study: Study, user: User) : this(
        task,
        StudyIdAndTitleDto(study),
        SimpleUserTasksDto(user),
        )


}
