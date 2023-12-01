package com.better.betterbackend.domain.study.service

import com.better.betterbackend.category.dao.CategoryRepository
import com.better.betterbackend.domain.challenge.dto.ChallengeUserDto
import com.better.betterbackend.domain.grouprank.dto.GroupRankDto
import com.better.betterbackend.domain.grouprankhistory.dto.GroupRankHistoryDto
import com.better.betterbackend.domain.grouprankhistory.dto.GroupRankHistoryUserDto
import com.better.betterbackend.domain.study.dto.request.StudyCreateRequestDto
import com.better.betterbackend.domain.study.dto.StudyDto
import com.better.betterbackend.domain.task.dto.response.TaskStudyUserDto
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.grouprank.domain.GroupRank
import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.member.dao.MemberRepository
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus
import com.better.betterbackend.taskgroup.dao.TaskGroupRepository
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import com.better.betterbackend.user.dao.UserRepository
import com.better.betterbackend.user.domain.User
import com.better.betterbackend.userrank.dao.UserRankRepository
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope.Empty

@Service
class StudyService(

    private val userRepository: UserRepository,

    private val studyRepository: StudyRepository,

    private val categoryRepository: CategoryRepository,

    private val memberRepository: MemberRepository,
    private val taskGroupRepository: TaskGroupRepository,//todo 테스트용 삭제
    private val userRankRepository: UserRankRepository,//todo 테스트용 삭제
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
        return StudyDto(
            studyRepository.findByIdOrNull(id)
                ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)
        )
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

        memberRepository.save(
            Member(
                study = study,
                user = user,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )

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

    fun getGroupRankHistory(studyId: Long): ArrayList<GroupRankHistoryUserDto> {
        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)
        val groupRankHistoryList1 = ArrayList<GroupRankHistoryUserDto>()

        for (groupRankHistory in study.groupRank.groupRankHistoryList) {
            if(study.groupRank.groupRankHistoryList==null){
                throw CustomException(ErrorCode.GROUPRANKHISTORY_NOT_FOUND)
            }
//            groupRankHistoryList1 += GroupRankHistoryDto(groupRankHistory)
            groupRankHistoryList1 += GroupRankHistoryUserDto(groupRankHistory)

        }
        return groupRankHistoryList1
    }


    fun getTask(studyId: Long): List<TaskStudyUserDto> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        // 로그인한 유저가 해당 스터디에 참여하지 않은 유저일 경우
        study.memberList.find { it.user.id!! == user.id!! } ?: throw CustomException(ErrorCode.NOT_PARTICIPATED)

        val taskGroupList = study.taskGroupList
            .find { it.status == TaskGroupStatus.INPROGRESS }!!

        return taskGroupList.taskList.map { TaskStudyUserDto(it, study, it.member.user) }
    }

    fun batch() {
        val date = LocalDate.now()
        val taskGroupList = taskGroupRepository.findAllByStatus(TaskGroupStatus.INPROGRESS)
//            .filter { it.endDate == date.minusDays(0) }//todo 1로바꿔야함

        for (taskGroup in taskGroupList) {
            val study = taskGroup.study!!
            val taskList = taskGroup.taskList
            val numOfMember = taskGroup.study!!.numOfMember
            var successCount = 0

            var userRankUpdateList = ArrayList<UserRank>()

            for (member in study.memberList) {
                var success = false
                val task = taskList.find { it.member.id == member.id }
                val userRank = member.user.userRank

                if (task?.challenge != null) {
                    // 승인 멤버가 절반을 넘은 경우
                    if (task.challenge!!.approve.size.toDouble() / (numOfMember - 1).toDouble() >= 0.5) {
                        success = true
                        successCount++
                    }
                }

                if (success) {
                    userRank.score += 20 // 성공했을 때 올라가는 점수
                    val userRankHistory = UserRankHistory(
                        score = 20,
                        description = "태스크 인증 완료",
                        userRank = userRank,
                        task = task!!,
                    )
                    userRank.userRankHistoryList += userRankHistory
                    userRankUpdateList.add(userRank)
                } else {
                    member.kickCount += 1
                    memberRepository.save(member)
                    if (member.kickCount == study.kickCondition) { // 퇴출 조건 만족시 퇴출 + 점수 깎기
                        userRank.score -= (300 + study.kickCondition * 200)
                        val userRankHistory = UserRankHistory(
                            score = -(300 + study.kickCondition * 200),
                            description = "태스크 인증 실패 횟수 초과로 점수감점후 퇴출",
                            userRank = member.user.userRank,
                            task = task,
                        )
                        userRank.userRankHistoryList += userRankHistory
                        userRankUpdateList.add(userRank)
                    }
                }
            }

            userRankRepository.saveAll(userRankUpdateList)

            // 전원 태스크 완료시 인원수*5만큼 점수 상승
            if (successCount == numOfMember) {
                userRankUpdateList = ArrayList()
                for (member in study.memberList) {
                    val task = taskList.find { it.member.id == member.id }
                    val userRank = member.user.userRank
                    val userRankHistory = UserRankHistory(
                        score = (5 * numOfMember),
                        description = "스터디 전원 태스크 완료 보너스 점수",
                        userRank = member.user.userRank,
                        task = task!!,
                    )
                    userRank.userRankHistoryList += userRankHistory
                    userRank.score += (5 * numOfMember)
                    userRankUpdateList.add(userRank)
                }
                userRankRepository.saveAll(userRankUpdateList)
            }

            val period = ChronoUnit.DAYS.between(study.createdAt.toLocalDate(), LocalDate.now()) // 그룹 랭크 점수 변경
            val totalReward = when (period) {
                in 1..182 -> {
                    25 * 0.3 + (successCount / numOfMember) * 70
                }

                in 183..364 -> {
                    50 * 0.3 + (successCount / numOfMember) * 70
                }

                else -> {//1년이상
                    100 * 0.3 + (successCount / numOfMember) * 70
                }
            }

            val groupRank = study.groupRank
            groupRank.score += totalReward.roundToInt()

            val groupRankHistory = GroupRankHistory(
                score = totalReward.roundToInt(),
                description = "그룹 리워드 정산",
                totalNumber = numOfMember,
                participantsNumber = successCount,
                groupRank = groupRank,
                taskGroup = taskGroup,
            )

            groupRank.groupRankHistoryList += groupRankHistory

            // 해당 태스크 그룹 종료
            taskGroup.groupRankHistory = groupRankHistory
            taskGroup.status = TaskGroupStatus.END

            // 다음 주기의 새로운 TaskGroup 생성
            var endDate = LocalDate.now()// 오늘
            if (study.period == Period.WEEKLY) {
                endDate = endDate.plusWeeks(1).minusDays(1)
            } else if (study.period == Period.BIWEEKLY) {
                endDate = endDate.plusWeeks(2).minusDays(1)
            }

            val newTaskGroup = TaskGroup(
                endDate = endDate,
                study = study
            )
            study.taskGroupList += newTaskGroup
            studyRepository.save(study)
        }

        val kickedMember = memberRepository.findAll().filter {
            it.memberType != MemberType.WITHDRAW && (it.kickCount == it.study!!.kickCondition || it.user.userRank.score < it.study!!.minRank)
        }
        for (member in kickedMember) {
            member.kickCount = 0
            member.memberType = MemberType.WITHDRAW
            member.study!!.numOfMember--
            memberRepository.save(member)
            studyRepository.save(member.study!!)
        }

    }


    fun test() { //todo 테스트용 나중에 삭제
        val user1 = userRepository.findByIdOrNull(1)
        val user2 = userRepository.findByIdOrNull(2)
        val user3 = userRepository.findByIdOrNull(3)
        val user4 = userRepository.findByIdOrNull(4)
        val user5 = userRepository.findByIdOrNull(5)
        val user6 = userRepository.findByIdOrNull(6)
        val user7 = userRepository.findByIdOrNull(7)
        val user8 = userRepository.findByIdOrNull(8)
        val user9 = userRepository.findByIdOrNull(9)
        val user10 = userRepository.findByIdOrNull(10)
        val study1 = studyRepository.findByIdOrNull(1)
        val study2 = studyRepository.findByIdOrNull(2)
        memberRepository.save(
            Member(
                study = study1,
                user = user2!!,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )
        study1!!.numOfMember++
        memberRepository.save(
            Member(
                study = study1,
                user = user3!!,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )
        study1!!.numOfMember++
        memberRepository.save(
            Member(
                study = study1,
                user = user4!!,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )
        study1!!.numOfMember++
        memberRepository.save(
            Member(
                study = study1,
                user = user5!!,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )
        study1!!.numOfMember++
        studyRepository.save(study1)


        memberRepository.save(
            Member(
                study = study2,
                user = user6!!,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )
        study2!!.numOfMember++
        memberRepository.save(
            Member(
                study = study2,
                user = user7!!,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )
        study2!!.numOfMember++
        memberRepository.save(
            Member(
                study = study2,
                user = user8!!,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )
        study2!!.numOfMember++
        memberRepository.save(
            Member(
                study = study2,
                user = user9!!,
                memberType = MemberType.MEMBER,
                // todo: 일단은 dummy 값, 추후 어떻게 할지 논의 필요
                notifyTime = LocalDateTime.now(),
            )
        )
        study2!!.numOfMember++
        studyRepository.save(study2)


    }
}