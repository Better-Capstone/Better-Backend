package com.better.betterbackend.global.exception.handler

import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.dto.ExceptionResponseDto
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(CustomException::class)
    protected fun handlerBaseException(
        e: CustomException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponseDto> {
        return ResponseEntity.status(e.errorCode.status).body(ExceptionResponseDto(e.errorCode, request.requestURI))
    }

}