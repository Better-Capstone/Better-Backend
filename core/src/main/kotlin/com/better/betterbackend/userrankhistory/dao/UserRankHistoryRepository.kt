package com.better.betterbackend.userrankhistory.dao

import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRankHistoryRepository: JpaRepository<UserRankHistory, Long> {
}