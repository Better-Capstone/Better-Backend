package com.better.betterbackend.domain.user.web
import com.better.betterbackend.domain.user.vo.OAuthTokenVo
import com.better.betterbackend.domain.user.service.UserService
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@RestController("/user")
class UserController(

    private val userService: UserService,

) {

    @PostMapping("/register")
    fun register(@RequestBody request: UserRegisterRequestDto): ResponseEntity<UserRegisterResponseDto> {
        return ResponseEntity.ok().body(userService.register(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody kakaoToken: String): ResponseEntity<UserLoginResponseDto> {
        return ResponseEntity.ok().body(userService.login(kakaoToken))
    }

    @GetMapping("/auth/kakao/callback")
    fun kakaoLogin(@RequestParam(name = "code", defaultValue = "Guest") code: String) : ResponseEntity<String> {
        return ResponseEntity.ok().body(userService.getKakaoAuthToken(code))
    }

}
