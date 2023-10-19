package com.better.betterbackend.userrank.dao

import com.better.betterbackend.userrank.domain.UserRank
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRankRepository: JpaRepository<UserRank, Long> {
}