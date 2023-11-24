package com.better.betterbackend.taskgroup.domain

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.model.BaseTimeEntity
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.task.domain.Task
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "task_group")
class TaskGroup(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "status")
    var status: TaskGroupStatus = TaskGroupStatus.INPROGRESS,

    @Column(name = "start_date")
    val startDate: LocalDate = LocalDate.now(),

    @Column(name = "end_date")
    val endDate: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    var study: Study? = null,

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "taskGroup",
        cascade = [CascadeType.REMOVE]
    )
    val taskList: List<Task> = ArrayList(),

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "group_rank_history")
    var groupRankHistory: GroupRankHistory? = null,

): BaseTimeEntity() {

}