package com.better.betterbackend.domain.study.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.domain.study.dto.request.StudyCreateRequestDto
import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto
import com.better.betterbackend.domain.study.dto.response.StudyResponseDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.member.dao.MemberRepository
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class StudyService(

    private val studyRepository: StudyRepository,

    private val categoryRepository: CategoryRepository,

    private val memberRepository: MemberRepository,

) {

    fun create(request: StudyCreateRequestDto): SimpleStudyResponseDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val category = categoryRepository.findByIdOrNull(request.categoryId) ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        val study = studyRepository.save(Study(
            owner = user,
            category = category,
            title = request.title,
            description = request.description,
            status = StudyStatus.INPROGRESS,
            period = request.period,
            checkDay = request.checkDay,
            numOfMember = 1,
            kickCondition = request.kickCondition,
            maximumCount = request.maximumCount,
            minRank = request.minRank,
        ))

        memberRepository.save(Member(
            study = study,
            user = user,
            kickCount = 0,
            memberType = MemberType.OWNER,
            notifyTime = LocalDateTime.now()
        ))

        return SimpleStudyResponseDto(study)
    }

    fun read(id: Long): StudyResponseDto {
        return StudyResponseDto(studyRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND))
    }

}