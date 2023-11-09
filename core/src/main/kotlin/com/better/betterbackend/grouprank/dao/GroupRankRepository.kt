package com.better.betterbackend.grouprank.dao

import com.better.betterbackend.grouprank.domain.GroupRank
import com.better.betterbackend.study.domain.Study
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRankRepository: JpaRepository<GroupRank, Long> {

}