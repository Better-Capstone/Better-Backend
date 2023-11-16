package com.better.betterbackend.domain.category.dto

import com.better.betterbackend.category.domain.Category
import java.time.LocalDateTime

data class SimpleCategoryDto(

    val id: Long,

    val name: String,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime,

) {

    constructor(category: Category): this(
        category.id!!,
        category.name,
        category.createdAt,
        category.updatedAt,
    )

}