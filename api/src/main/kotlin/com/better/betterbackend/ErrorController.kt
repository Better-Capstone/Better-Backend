package com.better.betterbackend

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
@Controller
class ErrorController : ErrorController {

    @GetMapping("/error")
    fun handleError(): String {
        // 원하는 에러 페이지 뷰로 리다이렉트 또는 포워드
        return "errorPage" // errorPage.html 또는 다른 에러 페이지 뷰 이름
    }

//    override fun getErrorPath(): String {
//        return "/error"
//    }
}