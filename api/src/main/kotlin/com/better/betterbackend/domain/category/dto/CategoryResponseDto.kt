package com.better.betterbackend.domain.category.dto

import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto

class CategoryResponseDto (

    val id: Long,

    val name: String,

    val studyList: List<SimpleStudyResponseDto>

) {

    constructor(category: Category): this(
        category.id!!,
        category.name,
        category.studyList.map { SimpleStudyResponseDto(it) },
    )

}