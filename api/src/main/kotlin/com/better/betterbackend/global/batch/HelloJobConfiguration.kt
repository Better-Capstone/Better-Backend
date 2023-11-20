package com.better.betterbackend.global.batch

import com.better.betterbackend.taskgroup.dao.TaskGroupRepository
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager

class CustomTasklet : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
//        val taskGroupRepository : TaskGroupRepository
//        taskGroupRepository.findByStatus(TaskGroupStatus.INPROGRESS)

        println("tasklet 1 complete")
        return RepeatStatus.FINISHED
    }
}

@Configuration
class HelloJobConfiguration (
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
){
    @Bean
    fun singleStepJob(): Job {
        return JobBuilder("singleStepJob",jobRepository)
            .start(singleStep())
            .build()
    }

    fun singleStep() : Step {
        return StepBuilder("singleStep",jobRepository)
            .tasklet(CustomTasklet(),transactionManager)
            .build()
    }
}




