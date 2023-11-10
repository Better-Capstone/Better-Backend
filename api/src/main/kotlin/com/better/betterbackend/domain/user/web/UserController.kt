package com.better.betterbackend.domain.user.web

import com.better.betterbackend.domain.challenge.response.ChallengeResponseDto
import com.better.betterbackend.domain.task.dto.response.TaskResponseDto
import com.better.betterbackend.domain.user.service.UserService
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserCheckResponseDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.better.betterbackend.domain.user.dto.response.UserResponseDto
import com.better.betterbackend.domain.userRankHistory.dto.response.UserRankHistoryResponseDto
import com.better.betterbackend.domain.userrank.dto.response.UserRankResponseDto
import com.better.betterbackend.global.validation.ValidationSequence
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(

    private val userService: UserService,

) {

    @Operation(summary = "유저 생성(테스트 용)")
    @GetMapping("/test")
    fun test(): ResponseEntity<List<String>> {
        return ResponseEntity.ok().body(userService.test())
    }

    @Operation(summary = "유저 가입 여부 확인")
    @GetMapping("/check")
    fun check(@RequestParam id: Long): ResponseEntity<UserCheckResponseDto> {
        return ResponseEntity.ok().body(userService.check(id))
    }

    @Operation(summary = "유저 가입")
    @PostMapping("/register")
    fun register(@RequestBody @Validated(value = [ValidationSequence::class]) request: UserRegisterRequestDto): ResponseEntity<UserRegisterResponseDto> {
        return ResponseEntity.ok().body(userService.register(request))
    }

    @Operation(summary = "유저 로그인")
    @PostMapping("/login")
    fun login(@RequestParam kakaoToken: String): ResponseEntity<UserLoginResponseDto> {
        return ResponseEntity.ok().body(userService.login(kakaoToken))
    }

    @Operation(summary = "아이디로 유저 조회")
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok().body(userService.getUser(id))

    }

    @Operation(summary = "유저 랭크 조회")
    @GetMapping("/{id}/rank")
    fun getRank(@PathVariable("id") id: Long): ResponseEntity<UserRankResponseDto> {
        return ResponseEntity.ok().body(userService.getRank(id))
    }

    @Operation(summary = "유저 랭크 기록 조회")
    @GetMapping("/{id}/rank/history")
    fun getRankHistory(@PathVariable("id") id: Long): ResponseEntity<List<UserRankHistoryResponseDto>> {
        return ResponseEntity.ok().body(userService.getRankHistory(id))
    }

    @Operation(summary = "유저 태스크 조회")
    @GetMapping("/{id}/tasks")
    fun getTask(@PathVariable("id") id: Long): ResponseEntity<List<TaskResponseDto>> {
        return ResponseEntity.ok().body(userService.getTask(id))

    }

    @Operation(summary = "유저 인증 조회")
    @GetMapping("/{id}/challenges")
    fun getChallenge(@PathVariable("id") id: Long): ResponseEntity<List<ChallengeResponseDto>> {
        return ResponseEntity.ok().body(userService.getChallenge(id))
    }

}

