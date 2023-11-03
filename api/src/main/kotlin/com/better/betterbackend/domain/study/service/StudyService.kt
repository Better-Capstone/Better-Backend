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
import com.better.betterbackend.user.dao.UserRepository
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class StudyService(

    private val userRepository: UserRepository,

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

    fun readById(id: Long): StudyResponseDto {
        return StudyResponseDto(studyRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND))
    }

    fun readByCategory(categoryId: Long): List<StudyResponseDto> {
        val category = categoryRepository.findByIdOrNull(categoryId) ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)

        return studyRepository.findStudiesByCategory(category).map { StudyResponseDto(it) }
    }

    fun readByUser(userId: Long): List<StudyResponseDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return memberRepository.findMembersByUser(user).map { StudyResponseDto(it.study) }
    }

    fun readInProgressStudies(): List<StudyResponseDto> {
        return studyRepository.findStudiesByStatus(StudyStatus.INPROGRESS).map { StudyResponseDto(it) }
    }

    fun joinStudy(studyId: Long) {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        memberRepository.save(Member(
            study = study,
            user = user,
            kickCount = 0,
            memberType = MemberType.MEMBER,
            // todo: notifyTime은 어떻게 지정할 지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))

        // todo: study 내 numOfMember 변수 업데이트 or 변수 삭제
    }

}