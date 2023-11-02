package com.better.betterbackend.domain.user.web
import com.better.betterbackend.domain.user.service.UserService
import com.better.betterbackend.domain.user.dto.request.UserRegisterRequestDto
import com.better.betterbackend.domain.user.dto.response.UserLoginResponseDto
import com.better.betterbackend.domain.user.dto.response.UserRegisterResponseDto
import com.better.betterbackend.domain.user.dto.response.UserResponseDto
import com.better.betterbackend.domain.user.service.KakaoService
import com.better.betterbackend.domain.userRankHistory.dto.UserRankHistoryResponseDto
import com.better.betterbackend.domain.userrank.dto.SimpleUserRankResponseDto
import com.better.betterbackend.domain.userrank.dto.UserRankResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/user")
class UserController(

    // todo: test 용 -> 삭제 필요
    private val kakaoService: KakaoService,

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
        // todo: test 용 -> 삭제 필요
        return ResponseEntity.ok().body(kakaoService.getKakaoAuthToken(code))
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long) : ResponseEntity<UserResponseDto> {
//        todo: header 에 jwt token 추가
        return ResponseEntity.ok().body(userService.getUser(id))

    }
    @GetMapping("/rank/{id}")
    fun getRank(@PathVariable("id") id: Long) : ResponseEntity<UserRankResponseDto>{
//        todo: header 에 jwt token 추가
        return ResponseEntity.ok().body(userService.getRank(id))
    }
    @GetMapping("/rank/{id}/history")
    fun getRankHistory(@PathVariable("id") id: Long) : ResponseEntity<List<UserRankHistoryResponseDto>>{
//        todo: header 에 jwt token 추가
        return ResponseEntity.ok().body(userService.getRankHistory(id))
    }

}
