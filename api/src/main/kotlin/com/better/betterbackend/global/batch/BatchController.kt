package com.better.betterbackend.global.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*
import kotlin.collections.HashMap

@RequestMapping("/batch")
@Controller
class BatchController (
    @Autowired
    private val jobLauncher : JobLauncher,
    @Autowired
    @Qualifier("singleStepJob")
    private val job1 :Job
)
{
    @GetMapping("/job1")
//    fun job1(): Long{
//        try {
//            val parameters: MutableMap<String, JobParameter<*>> = HashMap()
//            parameters["name"] = JobParameter("John Doe",String::class.java)
//            parameters["city"] = JobParameter("New York",String::class.java)
//
//            jobLauncher.run(job1, JobParameters(parameters))
//            return 333
//
//        } catch (ex: JobInstanceAlreadyCompleteException) {
//            return -111
//
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
    @Scheduled(cron = "0/5 * * * * ?") // 매일 자정에 실행
    fun runJob() {
        val jobParameters = JobParameters() // 필요에 따라 JobParameters를 설정할 수 있습니다.
        val jobExecution = jobLauncher.run(job1, jobParameters)
        println("Job Execution Status: ${jobExecution.status}")
    }

}