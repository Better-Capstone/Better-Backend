package com.better.betterbackend.global.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
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
class BatchScheduler (
    @Autowired
    private val jobLauncher : JobLauncher,
    @Autowired
    @Qualifier("singleStepJob")
    private val job1 :Job
)
{
//usertest -> studycreate x2 ->  studytest -> tasktest ->
    @Scheduled(cron = "0 18 21 * * ?") // 매일 자정에 실행 (0 0 0 * * ?)
    fun runJob() {
        //val jobParameters = JobParameters()
        val jobParameters = JobParametersBuilder()
            .addLocalDateTime("LocalDateTime", LocalDateTime.now())
            .toJobParameters()
        val jobExecution = jobLauncher.run(job1, jobParameters)
        println("Job Execution Status: ${jobExecution.status}")
    }

}