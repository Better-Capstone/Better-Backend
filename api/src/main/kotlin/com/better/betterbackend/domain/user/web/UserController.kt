package com.better.betterbackend.domain.user.web
import com.better.betterbackend.domain.user.service.UserService
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
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

    @GetMapping("/test2")
    fun test2(): ResponseEntity<String> {
        return ResponseEntity.ok().body(userService.hello())
    }

    @PostMapping("/register")
    fun register(@RequestBody request: UserRegisterRequestDto): ResponseEntity<UserRegisterResponseDto> {
        return ResponseEntity.ok().body(userService.register(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody kakaoToken: String): ResponseEntity<UserLoginResponseDto> {
        return ResponseEntity.ok().body(userService.login(kakaoToken))
    }

}
