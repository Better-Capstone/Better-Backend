package com.better.betterbackend.domain.user.web
import com.better.betterbackend.domain.challenge.ChallengeResponseDto
import com.better.betterbackend.domain.task.dto.TaskResponseDto
import com.better.betterbackend.domain.user.service.UserService
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.better.betterbackend.domain.user.dto.response.UserResponseDto
import com.better.betterbackend.domain.userRankHistory.dto.response.UserRankHistoryResponseDto
import com.better.betterbackend.domain.userrank.dto.UserRankResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(

    private val userService: UserService,

) {

    @GetMapping("/test/{nickname}")
    fun test(@PathVariable("nickname") nickname: String): ResponseEntity<String> {
        return ResponseEntity.ok().body(userService.test(nickname))
    }

    @PostMapping("/register")
    fun register(@RequestBody request: UserRegisterRequestDto): ResponseEntity<UserRegisterResponseDto> {
        return ResponseEntity.ok().body(userService.register(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody kakaoToken: String): ResponseEntity<UserLoginResponseDto> {
        return ResponseEntity.ok().body(userService.login(kakaoToken))
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok().body(userService.getUser(id))

    }

    @GetMapping("/{id}/rank")
    fun getRank(@PathVariable("id") id: Long): ResponseEntity<UserRankResponseDto> {
        return ResponseEntity.ok().body(userService.getRank(id))
    }

    @GetMapping("/{id}/rank/history")
    fun getRankHistory(@PathVariable("id") id: Long): ResponseEntity<List<UserRankHistoryResponseDto>> {
        return ResponseEntity.ok().body(userService.getRankHistory(id))
    }

    @GetMapping("/{id}/tasks")
    fun getTask(@PathVariable("id") id: Long): ResponseEntity<ArrayList<TaskResponseDto>> {
        return ResponseEntity.ok().body(userService.getTask(id))
        // todo 리턴타입 ArrayList-> List로 수정해주기
    }

    @GetMapping("/{id}/challenges")
    fun getChallenge(@PathVariable("id") id: Long): ResponseEntity<ArrayList<ChallengeResponseDto>> {
        return ResponseEntity.ok().body(userService.getChallenge(id))
    }
}

