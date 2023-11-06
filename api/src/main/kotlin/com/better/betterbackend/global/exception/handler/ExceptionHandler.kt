package com.better.betterbackend.global.exception.handler

import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.dto.ExceptionResponseDto
import com.better.betterbackend.global.exception.dto.ValidationErrorFieldDto
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(CustomException::class)
    protected fun handlerBaseException(
        e: CustomException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponseDto<String>> {
        return ResponseEntity.status(e.errorCode.status).body(
            ExceptionResponseDto(
                status = e.errorCode.status,
                requestURI = request.requestURI,
                data = e.errorCode.message,
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun validationException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponseDto<List<ValidationErrorFieldDto>>> {
        val errors = e.bindingResult.fieldErrors

        val data: ArrayList<ValidationErrorFieldDto> = ArrayList()
        for (error in errors) {
            data.add(ValidationErrorFieldDto(error.field, error.defaultMessage!!))
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ExceptionResponseDto(
                status = HttpStatus.BAD_REQUEST,
                requestURI = request.requestURI,
                data = data
            )
        )
    }

}