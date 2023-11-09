package com.better.betterbackend.domain.category.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.domain.category.dto.response.CategoryResponseDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService (

    private val categoryRepository: CategoryRepository,

) {

    fun create(name: String): Long {
        val category = Category(
            name = name
        )

        return categoryRepository.save(category).id!!
    }

    fun read(id: Long): CategoryResponseDto {
        val category = categoryRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)

        return CategoryResponseDto(category)
    }

}