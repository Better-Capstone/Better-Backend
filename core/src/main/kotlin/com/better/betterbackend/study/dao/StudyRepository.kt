package com.better.betterbackend.study.dao

import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StudyRepository: JpaRepository<Study, Long> {

    fun findStudiesByCategory(category: Category): List<Study>

    fun findStudiesByStatus(status: StudyStatus): List<Study>

    @Query("SELECT s FROM Study s WHERE (:category is null or s.category = :category) and (:keyword is null or s.title LIKE CONCAT('%',:keyword,'%'))")
    fun findStudiesByCategoryAndTitleContaining(category: Category?, keyword: String?): List<Study>

}