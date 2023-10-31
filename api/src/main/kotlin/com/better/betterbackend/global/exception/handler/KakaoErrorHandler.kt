package com.better.betterbackend.global.exception.handler

import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.DefaultResponseErrorHandler

class KakaoErrorHandler: DefaultResponseErrorHandler() {

    override fun handleError(response: ClientHttpResponse) {
        if (response.statusCode == HttpStatus.UNAUTHORIZED) {
            throw CustomException(ErrorCode.KAKAO_UNAUTHORIZED)
        }
    }

}