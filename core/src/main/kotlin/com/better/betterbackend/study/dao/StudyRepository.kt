package com.better.betterbackend.study.dao

import com.better.betterbackend.study.domain.Study
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyRepository: JpaRepository<Study, Long> {
}