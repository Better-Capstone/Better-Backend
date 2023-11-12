package com.better.betterbackend.domain.task.web

import com.better.betterbackend.domain.task.dto.request.TaskRegisterRequestDto
import com.better.betterbackend.domain.task.dto.response.TaskResponseDto
import com.better.betterbackend.domain.task.service.TaskService
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController
@RestController
@RequestMapping("/task")
class TaskController (
    private val taskService: TaskService,
){
    @PostMapping("/register")
    fun register(@RequestBody request: TaskRegisterRequestDto): ResponseEntity<TaskResponseDto> {
        return ResponseEntity.ok().body(taskService.register(request))
    }
}