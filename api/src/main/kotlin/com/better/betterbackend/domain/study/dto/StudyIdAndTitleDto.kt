package com.better.betterbackend.domain.study.dto

import com.better.betterbackend.study.domain.Study

class StudyIdAndTitleDto(

    val id: Long,

    val title: String,

) {

    constructor(study: Study): this(
        study.id!!,
        study.title
    )

}