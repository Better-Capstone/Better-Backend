package com.better.betterbackend.domain.challenge.web

import com.better.betterbackend.domain.challenge.dto.request.ChallengeApproveRequestDto
import com.better.betterbackend.domain.challenge.dto.request.ChallengeRegisterRequestDto
import com.better.betterbackend.domain.challenge.dto.response.ChallengeResponseDto
import com.better.betterbackend.domain.challenge.service.ChallengeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/challenge")
class ChallengeController(
    private val challengeService: ChallengeService,
) {
    @PostMapping("/register")
    fun register(@RequestBody request: ChallengeRegisterRequestDto): ResponseEntity<ChallengeResponseDto> {
        return ResponseEntity.ok().body(challengeService.register(request))
    }
    @GetMapping("/{id}")
    fun getChallenge(@PathVariable("id") id: Long): ResponseEntity<ChallengeResponseDto>{
        return ResponseEntity.ok().body(challengeService.getChallenge(id))
    }
    @PostMapping("/comment/:{id}")
    fun approve(@PathVariable("id") id: Long,request: ChallengeApproveRequestDto): ResponseEntity<Any>{
        return ResponseEntity.ok().body(challengeService.approve(id,request))
    }

}