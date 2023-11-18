package com.better.betterbackend.domain.category.dto

import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.domain.study.dto.SimpleStudyDto
import java.time.LocalDateTime

data class CategoryDto (

    val id: Long,

    val name: String,

    val studyList: List<SimpleStudyDto>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(category: Category): this(
        category.id!!,
        category.name,
        category.studyList.map { SimpleStudyDto(it) },
        category.createdAt,
        category.updatedAt,
    )

}