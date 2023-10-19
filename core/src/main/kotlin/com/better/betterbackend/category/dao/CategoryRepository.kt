package com.better.betterbackend.category.dao

import com.better.betterbackend.category.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
}