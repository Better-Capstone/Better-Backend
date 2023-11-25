package com.better.betterbackend

import jakarta.annotation.PostConstruct
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
class BatchApplication

@PostConstruct
fun started() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
}

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}

