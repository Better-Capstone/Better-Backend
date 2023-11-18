package com.better.betterbackend.domain.challenge.web

import com.better.betterbackend.domain.challenge.dto.ChallengeDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeApproveRequestDto
import com.better.betterbackend.domain.challenge.service.ChallengeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/challenge")
class ChallengeController(

    private val challengeService: ChallengeService,

) {

    @Operation(summary = "챌린지 조회")
    @GetMapping("/{id}")
    fun getChallenge(@PathVariable("id") id: Long): ResponseEntity<ChallengeDto> {
        return ResponseEntity.ok().body(challengeService.getChallenge(id))
    }

    @Operation(summary = "챌린지 인증")
    @PostMapping("/comment/:{id}")
    fun approve(@PathVariable("id") id: Long, @RequestBody request: ChallengeApproveRequestDto): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(challengeService.approve(id,request))
    }

}