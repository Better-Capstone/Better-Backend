package com.better.betterbackend.domain.task.dto.response

import com.better.betterbackend.domain.study.dto.StudyTitleDto
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.user.dto.UserNicknameScoreDto
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.user.domain.User

class TaskStudyUserResponseDto(

    task: Task,

    val study: StudyTitleDto,

    val user: UserNicknameScoreDto,

) : TaskDto(task) {

    constructor(task: Task, study: Study, user: User) : this(
        task,
        StudyTitleDto(study),
        UserNicknameScoreDto(user),
    )

}
