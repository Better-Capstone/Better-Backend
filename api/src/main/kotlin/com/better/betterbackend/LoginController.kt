package com.better.betterbackend
import com.better.betterbackend.domain.user.domain.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory

class UserInfo(
    var id:Long,
    var connected_at:String,
    var kakao_account:KakaoAccount

)
class KakaoAccount(
    var profile_nickname_needs_agreement:Boolean,
    var profile: Profile
)
class Profile(
    var nickname:String
)

@RestController
class LoginController(private val userService: UserService) {
    @GetMapping("/auth/kakao/callback")
    fun kakaoLogin(@RequestParam(name = "code", defaultValue = "Guest") code: String) :ResponseEntity<String>{
//      카카오 인증토큰 받기
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val requestBody = "grant_type=authorization_code" +
                "&client_id=8d5ea40d2be91e2927e726e69eeda1b5" +
                "&redirect_uri=http://127.0.0.1:8080/auth/kakao/callback" +
                "&code=$code"
        val requestEntity = HttpEntity(requestBody, headers)

        val url = "https://kauth.kakao.com/oauth/token"

        val responseEntity: ResponseEntity<String> = restTemplate.postForEntity(url, requestEntity, String::class.java)

//        인증토큰 받아서 OAuthToken에 저장하기
        val jsonResponse = responseEntity.body
        println(jsonResponse)
        val objectMapper = ObjectMapper()
        val oAuthToken = objectMapper.readValue(jsonResponse, OAuthToken::class.java)
        println(oAuthToken.access_token)
//        val redirectView = RedirectView()

//        redirectView.url = "/auth/kakao/userinfo?access_token=${OAuthToken.access_token}"

        val restTemplate1 = RestTemplate()
        restTemplate1.setRequestFactory(HttpComponentsClientHttpRequestFactory())
        val url1 = "https://kapi.kakao.com/v2/user/me"
        val headers1 = HttpHeaders()
        headers1.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers1.set("Authorization","Bearer ${oAuthToken.access_token}")
//        val requestBody1 = "property_keys=[\"kakao_account.email\"]"
        val requestBody1 = "property_keys=[\"kakao_account.profile\"]"
        val requestEntity1 = HttpEntity(requestBody1,headers1)
        val responseEntity1: ResponseEntity<String> = restTemplate1.postForEntity(url1, requestEntity1, String::class.java)
        val jsonResponse1 = responseEntity1.body
        println(jsonResponse1)
        val objectMapper1 = ObjectMapper().registerModule(KotlinModule())
        val userInfo = objectMapper1.readValue(jsonResponse1, UserInfo::class.java)
        userService.saveUser(userInfo.id, userInfo.kakao_account.profile.nickname)
        println(userInfo.id)
        println(userInfo.kakao_account.profile.nickname)

//        val id = userInfo.id
//        val nickname = userInfo.kakao_account.profile.nickname









//        return "카카오 인증완료 $code \nResponse status code: ${responseEntity.statusCode}\n Response body: ${responseEntity.body}"
//        return "Access Token: ${OAuthToken.access_token}          Expires In : ${OAuthToken.expires_in}"
//        return "Redirect:/auth/kakao/userinfo?access_token=${OAuthToken.access_token}"
        return responseEntity1
    }


}

