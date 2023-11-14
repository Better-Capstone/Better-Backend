package com.better.betterbackend.task.dao

import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.task.domain.TaskStatus
import com.better.betterbackend.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<Task, Long> {
    fun findByMemberAndStudy(member: Member, study: Study): Task?
    fun findtaskByStatus(status: TaskStatus): Task

}