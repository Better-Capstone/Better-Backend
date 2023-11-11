package com.better.betterbackend.domain.category.dto

import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.domain.study.dto.SimpleStudyDto

data class CategoryDto (

    val id: Long,

    val name: String,

    val studyList: List<SimpleStudyDto>

) {

    constructor(category: Category): this(
        category.id!!,
        category.name,
        category.studyList.map { SimpleStudyDto(it) },
    )

}