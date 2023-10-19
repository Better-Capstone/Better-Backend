package com.better.betterbackend.grouprankhistory.dao

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRankHistoryRepository: JpaRepository<GroupRankHistory, Long> {
}