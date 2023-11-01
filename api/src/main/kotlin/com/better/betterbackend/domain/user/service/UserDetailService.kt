package com.better.betterbackend.domain.user.service

import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.user.dao.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(

    private val userRepository: UserRepository,

): UserDetailsService {

    override fun loadUserByUsername(userPK: String): UserDetails {
        return userRepository.findByIdOrNull(userPK.toLong()) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
    }

}