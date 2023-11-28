package com.better.betterbackend.domain.task.web

import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeRegisterRequestDto
import com.better.betterbackend.domain.challenge.service.ChallengeService
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.task.dto.request.TaskRegisterRequestDto
import com.better.betterbackend.domain.task.service.TaskService
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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
    @PostMapping("/{id}/challenge/register", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun challengeRegister(
        @PathVariable("id") taskId: Long,
        @RequestPart(value="image") image: MultipartFile,
        @RequestPart(value="request") request: ChallengeRegisterRequestDto,
    ): ResponseEntity<ChallengeDto> {
        return ResponseEntity.ok().body(challengeService.register(taskId, request, image))
    }

}