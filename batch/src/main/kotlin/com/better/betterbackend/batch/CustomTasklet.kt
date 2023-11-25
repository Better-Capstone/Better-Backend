package com.better.betterbackend.batch

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.member.dao.MemberRepository
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.taskgroup.dao.TaskGroupRepository
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import com.better.betterbackend.userrank.dao.UserRankRepository
import com.better.betterbackend.userrank.domain.UserRank
import com.better.betterbackend.userrankhistory.domain.UserRankHistory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

class CustomTasklet(

    private val taskGroupRepository: TaskGroupRepository,

    private val userRankRepository: UserRankRepository,

    private val studyRepository: StudyRepository,

    private val memberRepository: MemberRepository,

) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val date = LocalDate.now()
        val taskGroupList = taskGroupRepository.findAllByStatus(TaskGroupStatus.INPROGRESS)
            .filter { it.endDate == date.minusDays(1) }

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

        return RepeatStatus.FINISHED
    }

}