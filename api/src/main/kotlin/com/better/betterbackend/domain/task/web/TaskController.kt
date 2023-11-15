package com.better.betterbackend.domain.task.web

import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeRegisterRequestDto

import com.better.betterbackend.domain.challenge.service.ChallengeService
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.task.dto.request.TaskRegisterRequestDto
import com.better.betterbackend.domain.task.service.TaskService
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

    @PostMapping("/register")
    fun register(@RequestBody request: TaskRegisterRequestDto): ResponseEntity<TaskDto> {
        return ResponseEntity.ok().body(taskService.register(request))
    }

    @PostMapping("/{id}/challenge/register")
    fun challengeRegister(
        @RequestBody request: ChallengeRegisterRequestDto, @PathVariable("id") taskId:Long
    ): ResponseEntity<ChallengeDto>{
        return ResponseEntity.ok().body(challengeService.register(request, taskId))
    }

}