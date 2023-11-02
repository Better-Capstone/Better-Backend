package com.better.betterbackend.domain.study.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.domain.study.dto.request.StudyCreateRequestDto
import com.better.betterbackend.domain.study.dto.response.StudyResponseDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class StudyService(

    private val studyRepository: StudyRepository,

    private val categoryRepository: CategoryRepository,

) {

    fun create(request: StudyCreateRequestDto): StudyResponseDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val category = categoryRepository.findByIdOrNull(request.categoryId) ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        val study = studyRepository.save(Study(
            user,
            category,
            request.title,
            request.description,
            request.checkDay,
            request.kickCondition,
            request.maximumCount,
            request.minRank,
            request.period
        ))

        return StudyResponseDto(study)
    }

}