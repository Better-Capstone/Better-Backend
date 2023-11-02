package com.better.betterbackend.domain.category.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.category.domain.Category
import org.springframework.stereotype.Service

@Service
class CategoryService (

    private val categoryRepository: CategoryRepository,

) {

    fun create(name: String): Long {
        val category = Category(null, name, emptyList())
        return categoryRepository.save(category).id!!
    }

}