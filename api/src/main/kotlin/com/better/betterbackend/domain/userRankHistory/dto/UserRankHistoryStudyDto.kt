package com.better.betterbackend.domain.userRankHistory.dto

import com.better.betterbackend.domain.study.dto.StudyTitleDto
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.userrankhistory.domain.UserRankHistory

class UserRankHistoryStudyDto(

    userRankHistory: UserRankHistory,

    val study: StudyTitleDto,

): UserRankHistoryDto(userRankHistory) {

    constructor(userRankHistory: UserRankHistory, study: Study) : this(
        userRankHistory,
        StudyTitleDto(study)
    )

}