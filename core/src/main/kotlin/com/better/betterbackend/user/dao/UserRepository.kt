package com.better.betterbackend.user.dao

import org.springframework.boot.autoconfigure.security.SecurityProperties.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
}