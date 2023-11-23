package com.better.betterbackend.config

import com.better.betterbackend.batch.CustomTasklet
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.taskgroup.dao.TaskGroupRepository
import com.better.betterbackend.userrank.dao.UserRankRepository
import com.better.betterbackend.userrankhistory.dao.UserRankHistoryRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class BatchJobConfiguration(

    private val taskGroupRepository: TaskGroupRepository,

    private val userRankRepository: UserRankRepository,

    private val studyRepository: StudyRepository,

    private val userRankHistoryRepository: UserRankHistoryRepository,

    private val jobRepository: JobRepository,

    @Qualifier("batchTransactionManager")
    private val transactionManager: PlatformTransactionManager

) {

    @Bean
    fun singleStepJob(): Job {
        return JobBuilder("singleStepJob", jobRepository)
            .start(singleStep())
            .build()
    }

    fun singleStep(): Step {
        return StepBuilder("singleStep", jobRepository)
            .tasklet(
                CustomTasklet(taskGroupRepository, userRankRepository, studyRepository, userRankHistoryRepository),
                transactionManager
            )
            .build()
    }

}
