package com.better.betterbackend.taskgroup.dao

import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import org.springframework.data.jpa.repository.JpaRepository

interface TaskGroupRepository: JpaRepository<TaskGroup, Long> {

    fun findAllByStatus(taskGroupStatus: TaskGroupStatus): List<TaskGroup>

}