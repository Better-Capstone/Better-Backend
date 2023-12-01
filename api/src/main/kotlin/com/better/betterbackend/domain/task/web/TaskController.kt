package com.better.betterbackend.domain.task.web

import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeRegisterRequestDto
import com.better.betterbackend.domain.challenge.service.ChallengeService
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.task.dto.request.TaskRegisterRequestDto
import com.better.betterbackend.domain.task.service.TaskService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task")
class TaskController (

    private val taskService : TaskService,

    private val challengeService : ChallengeService,

){

    @Operation(summary = "태스크 등록")
    @PostMapping("/register")
    fun register(@RequestBody request: TaskRegisterRequestDto): ResponseEntity<TaskDto> {
        return ResponseEntity.ok().body(taskService.register(request))
    }

    @Operation(summary = "태스크 아이디로 조회")
    @GetMapping("/{id}")
    fun getTaskById(@PathVariable("id") id: Long): ResponseEntity<TaskDto> {
        return ResponseEntity.ok().body(taskService.getTaskById(id))
    }

    @Operation(summary = "태스크에 대한 챌린지 등록")
    @PostMapping("/{id}/challenge/register")
    fun challengeRegister(
        @RequestBody request: ChallengeRegisterRequestDto, @PathVariable("id") taskId: Long
    ): ResponseEntity<ChallengeDto> {
        return ResponseEntity.ok().body(challengeService.register(request, taskId))
    }
    @PostMapping("/test")
    fun test(): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(taskService.test())
    }

}