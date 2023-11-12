package com.better.betterbackend.domain.category.dto

import com.better.betterbackend.category.domain.Category

data class SimpleCategoryDto(

    val id: Long,

    val name: String,

) {

    constructor(category: Category): this(
        category.id!!, category.name
    )

}