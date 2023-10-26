package com.better.betterbackend.domain.user.dao

import com.better.betterbackend.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
}