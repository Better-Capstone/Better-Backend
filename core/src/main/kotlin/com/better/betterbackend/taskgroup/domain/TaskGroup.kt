package com.better.betterbackend.taskgroup.domain

import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class TaskGroup(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var status: TaskGroupStatus = TaskGroupStatus.INPROGRESS,

    @ManyToOne
    var study: Study? = null,

    val startDate: LocalDate = LocalDate.now(),

    val endDate: LocalDate,

    @OneToMany(mappedBy = "taskGroup", cascade = [CascadeType.REMOVE])
    val taskList: List<Task> = ArrayList(),

): BaseTimeEntity() {

}