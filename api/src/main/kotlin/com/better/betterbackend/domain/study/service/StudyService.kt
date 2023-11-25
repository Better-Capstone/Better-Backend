package com.better.betterbackend.domain.study.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.domain.grouprank.dto.GroupRankDto
import com.better.betterbackend.domain.grouprankhistory.dto.GroupRankHistoryDto
import com.better.betterbackend.domain.study.dto.request.StudyCreateRequestDto
import com.better.betterbackend.domain.study.dto.StudyDto
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.grouprank.domain.GroupRank
import com.better.betterbackend.member.dao.MemberRepository
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.study.domain.CheckDay
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import com.better.betterbackend.user.dao.UserRepository
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class StudyService(

    private val userRepository: UserRepository,

    private val studyRepository: StudyRepository,

    private val categoryRepository: CategoryRepository,

    private val memberRepository: MemberRepository,

) {

    fun create(request: StudyCreateRequestDto): StudyDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        // 유저의 랭크가 만들고자 하는 스터디의 최소 점수보다 낮은 경우
        if (user.userRank.score < request.minRank!!) {
            throw CustomException(ErrorCode.UNDER_MIN_RANK)
        }

        // 카테고리가 존재하지 않는 경우
        val category = categoryRepository.findByIdOrNull(request.categoryId)
            ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)

        val groupRank = GroupRank()

        val member = Member(
            user = user,
            memberType = MemberType.OWNER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        )

        // 새로 생성된 Study의 첫 TaskGroup 종료 시각 결정
        var endDate = LocalDate.now()
        if (request.period!! != Period.EVERYDAY) {
            if (request.period == Period.BIWEEKLY) {
                endDate = endDate.plusWeeks(1)
            }
            var day: Int = request.checkDay!!.ordinal
            val currentDay = LocalDate.now().dayOfWeek.value
            if (day <= currentDay) {
                day += 7
            }
            endDate = endDate.plusDays((day - currentDay).toLong())
        }

        val taskGroup = TaskGroup(
            endDate = endDate
        )

        val study = Study(
            owner = user,
            category = category,
            title = request.title!!,
            description = request.description!!,
            period = request.period,
            checkDay = request.checkDay!!,
            kickCondition = request.kickCondition!!,
            maximumCount = request.maximumCount!!,
            minRank = request.minRank,
            memberList = arrayListOf(member),
            taskGroupList = arrayListOf(taskGroup),
            groupRank = groupRank,
        )

        member.study = study
        taskGroup.study = study
        groupRank.study = study
        study.numOfMember++
        studyRepository.save(study)

        return StudyDto(study)
    }

    fun getStudyById(id: Long): StudyDto {
        return StudyDto(studyRepository.findByIdOrNull(id)
            ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND))
    }

    fun getStudyByCategory(categoryId: Long): List<StudyDto> {
        val category = categoryRepository.findByIdOrNull(categoryId)
            ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)

        return studyRepository.findStudiesByCategory(category).map { StudyDto(it) }
    }

    fun getStudyByUser(userId: Long): List<StudyDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        // 유저가 탈퇴한 스터디 필터링
        return user.memberList
            .filter { it.memberType != MemberType.WITHDRAW }
            .map { StudyDto(it.study!!) }
    }

    fun getInProgressStudies(): List<StudyDto> {
        return studyRepository.findStudiesByStatus(StudyStatus.INPROGRESS).map { StudyDto(it) }
    }

    fun joinStudy(studyId: Long) {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        // 정원을 초과하는 경우
        val memberList = study.memberList.filter { it.memberType != MemberType.WITHDRAW }
        if (memberList.size == study.maximumCount) {
            throw CustomException(ErrorCode.OVER_CAPACITY)
        }

        // 유저의 랭크 점수가 최저 점수 보다 낮은 경우
        if (user.userRank.score < study.minRank) {
            throw CustomException(ErrorCode.UNDER_MIN_RANK)
        }

        // 유저가 이미 가입된 상태일 경우
        val member = study.memberList.find { it.user.id!! == user.id!! }
        if (member != null && member.memberType != MemberType.WITHDRAW) {
            throw CustomException(ErrorCode.ALREADY_PARTICIPATED)
        }

        memberRepository.save(Member(
            study = study,
            user = user,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))

        study.numOfMember++
        studyRepository.save(study)
    }

    fun getUsersInStudy(studyId: Long): List<UserDto> {
        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        return study.memberList
            .filter { it.memberType != MemberType.WITHDRAW }
            .map { UserDto(it.user) }
    }

    fun getStudyByKeywordAndCategory(categoryId: Long?, keyword: String?): List<StudyDto> {
        val category = if (categoryId == null) {
            null
        } else {
            categoryRepository.findByIdOrNull(categoryId) ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        }

        return studyRepository.findStudiesByCategoryAndTitleContaining(category, keyword)
            .map { StudyDto(it) }
    }

    fun getGroupRank(studyId: Long): GroupRankDto {
        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        return GroupRankDto(study.groupRank)
    }

    fun getGroupRankHistory(studyId: Long): List<GroupRankHistoryDto> {
        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        return study.groupRank.groupRankHistoryList.map { GroupRankHistoryDto(it) }
    }

    fun getTask(studyId: Long): List<TaskDto> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        // 로그인한 유저가 해당 스터디에 참여하지 않은 유저일 경우
        study.memberList.find { it.user.id!! == user.id!! } ?: throw CustomException(ErrorCode.NOT_PARTICIPATED)

        val taskGroupList = study.taskGroupList
            .find { it.status == TaskGroupStatus.INPROGRESS }!!

        return taskGroupList.taskList.map { TaskDto(it) }
    }

    fun test() {
        val user1 = userRepository.findByIdOrNull(1)!!
        val groupRank1 = GroupRank()
        val member1 = Member(
            user = user1,
            memberType = MemberType.OWNER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        )
        val taskGroup1 = TaskGroup(
            endDate = LocalDate.now()
        )
        val study1 = studyRepository.save(Study(
            owner = user1,
            category = categoryRepository.findByIdOrNull(1)!!,
            title = "study1",
            description = "study1",
            period = Period.EVERYDAY,
            checkDay = CheckDay.EVERYDAY,
            kickCondition = 1,
            maximumCount = 50,
            minRank = 100,
            groupRank = groupRank1,
            memberList = arrayListOf(member1),
            taskGroupList = arrayListOf(taskGroup1)
        ))
        member1.study = study1
        taskGroup1.study = study1
        groupRank1.study = study1
        study1.numOfMember++
        studyRepository.save(study1)

        val groupRank2 = GroupRank()
        val member2 = Member(
            user = user1,
            memberType = MemberType.OWNER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        )
        val taskGroup2 = TaskGroup(
            endDate = LocalDate.now()
        )
        val study2 = studyRepository.save(Study(
            owner = user1,
            category = categoryRepository.findByIdOrNull(1)!!,
            title = "study1",
            description = "study1",
            period = Period.EVERYDAY,
            checkDay = CheckDay.EVERYDAY,
            kickCondition = 1,
            maximumCount = 50,
            minRank = 100,
            groupRank = groupRank2,
            memberList = arrayListOf(member2),
            taskGroupList = arrayListOf(taskGroup2)
        ))
        member2.study = study2
        taskGroup2.study = study2
        groupRank2.study = study2
        study2.numOfMember++
        studyRepository.save(study2)

        val user2 = userRepository.findByIdOrNull(2)
        val user3 = userRepository.findByIdOrNull(3)
        val user4 = userRepository.findByIdOrNull(4)
        val user5 = userRepository.findByIdOrNull(5)
        val user6 = userRepository.findByIdOrNull(6)
        val user7 = userRepository.findByIdOrNull(7)
        val user8 = userRepository.findByIdOrNull(8)
        val user9 = userRepository.findByIdOrNull(9)
        val user10 = userRepository.findByIdOrNull(10)
        study1!!.memberList += memberRepository.save(Member(
            study = study1,
            user = user2!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))
        study1!!.memberList += memberRepository.save(Member(
            study = study1,
            user = user3!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))
        study1!!.memberList += memberRepository.save(Member(
            study = study1,
            user = user4!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))
        study1!!.memberList += memberRepository.save(Member(
            study = study1,
            user = user5!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))

        study1!!.numOfMember += 4
        studyRepository.save(study1!!)


        study2!!.memberList += memberRepository.save(Member(
            study = study2,
            user = user6!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))
        study2!!.memberList += memberRepository.save(Member(
            study = study2,
            user = user7!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))
        study2!!.memberList += memberRepository.save(Member(
            study = study2,
            user = user8!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))
        study2!!.memberList += memberRepository.save(Member(
            study = study2,
            user = user9!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))
        study2!!.memberList += memberRepository.save(Member(
            study = study2,
            user = user10!!,
            memberType = MemberType.MEMBER,
            // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
            notifyTime = LocalDateTime.now(),
        ))
        study2!!.numOfMember += 5
        studyRepository.save(study2!!)
    }
}