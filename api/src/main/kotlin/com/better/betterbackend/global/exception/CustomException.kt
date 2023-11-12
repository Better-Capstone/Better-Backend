package com.better.betterbackend.global.exception

data class CustomException(val errorCode: ErrorCode): RuntimeException()