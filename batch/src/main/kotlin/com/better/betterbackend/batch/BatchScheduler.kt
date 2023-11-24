package com.better.betterbackend.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime

@RequestMapping("/batch")
@Controller
class BatchScheduler(

    private val jobLauncher: JobLauncher,

    @Qualifier(value = "batchJob")
    private val job: Job

) {

    @Scheduled(cron = "0 * * * * *") // 매일 자정에 실행 (0 0 0 * * ?)
    fun runJob() {
        val jobParameters = JobParametersBuilder()
            .addLocalDateTime("LocalDateTime", LocalDateTime.now())
            .toJobParameters()
        val jobExecution = jobLauncher.run(job, jobParameters)
        println("Job Execution Status: ${jobExecution.status}")
    }

}