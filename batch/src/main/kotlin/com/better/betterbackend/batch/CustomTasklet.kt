package com.better.betterbackend.batch

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.taskgroup.dao.TaskGroupRepository
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import com.better.betterbackend.userrank.dao.UserRankRepository
import com.better.betterbackend.userrankhistory.dao.UserRankHistoryRepository
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

    private val userRankHistoryRepository: UserRankHistoryRepository

) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val date = LocalDate.now()
        val taskGroupList = taskGroupRepository.findAllByStatus(TaskGroupStatus.INPROGRESS)
            .filter { it.endDate == date }

        for (taskGroup in taskGroupList) {//어차피 지금 이 태스크 그룹은 같은 스터디니까?
            val study = taskGroup.study!!
            val taskList = taskGroup.taskList
            val numOfMember = taskGroup.study!!.numOfMember
            var successCount = 0
            for (member in study.memberList) {
                var success = false
                // task, challenge가 null이 아니고 approve가 일정 퍼센트를 넘었을 경우
                val task = taskList.find { it.member.id == member.id }
                println(task)

                if (task?.challenge != null) {
                    // 승인멤버가 과반수를 넘은 경우
                    if (0.5 < task.challenge!!.approve.size.toDouble() / numOfMember.toDouble()) {
                        success = true
                        successCount++
                    }
                }
                if (success) {
                    member.user.userRank.score += 20 // 성공했을 때 올라가는 점수
                    val userRankHistory = UserRankHistory(
                        score = 20,
                        description = "태스크 인증 완료",
                        userRank = member.user.userRank,
                        task = task!!,
                    )

                    userRankHistoryRepository.save(userRankHistory)
                    member.user.userRank.userRankHistoryList += userRankHistory
                    userRankRepository.save(member.user.userRank)
                } else {
                    member.kickCount += 1
                    if (member.kickCount == study.kickCondition) {//퇴출조건 만족시 퇴출 + 점수깎기
                        val userRankHistory = UserRankHistory(
                            score = -(300 + study.kickCondition * 200),
                            description = "태스크 인증 실패 횟수 초과로 점수감점후 퇴출",
                            userRank = member.user.userRank,
                            task = task!!,
                        )

                        userRankHistoryRepository.save(userRankHistory)

                        member.memberType = MemberType.WITHDRAW
                        member.user.userRank.score -= (300 + study.kickCondition * 200)
                    }
                }
            }

            if (successCount == numOfMember) {//전원 태스크 완료시 인원수*5만큼 점수상승
                for (member in study.memberList) {
                    val task = taskList.find { it.member.id == member.id }
                    println(task)
                    val userRankHistory = UserRankHistory(
                        score = (5 * numOfMember),
                        description = "스터디 전원 태스크완료 보너스 점수",
                        userRank = member.user.userRank,
                        task = task!!,
                    )
                    println(userRankHistory)
                    member.user.userRank.userRankHistoryList += userRankHistory
                    member.user.userRank.score += (5 * numOfMember)
                    userRankHistoryRepository.save(userRankHistory)
                }
            }

            val period = ChronoUnit.DAYS.between(study.createdAt.toLocalDate(), date) //그룹랭크 점수 변경
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

            study.groupRank.score += totalReward.roundToInt()

            val groupRankHistory = GroupRankHistory(
                score = totalReward.roundToInt(),
                description = "그룹리워드 정산",
                totalNumber = numOfMember,
                participantsNumber = successCount,
                groupRank = study.groupRank,
                taskGroup = taskGroup,
            )
            study.groupRank.groupRankHistoryList += groupRankHistory

            // 해당 태스크 그룹 종료
            taskGroup.status = TaskGroupStatus.END

            // 다음 주기의 새로운 TaskGroup 생성
            val endDate = LocalDate.now()// 오늘
            when (study.period) {
                Period.EVERYDAY -> {
                    endDate.plusDays(1)
                }

                Period.WEEKLY -> {
                    endDate.plusWeeks(1)
                }

                Period.BIWEEKLY -> {
                    endDate.plusWeeks(2)
                }
            }
            val newTaskGroup = TaskGroup(
                endDate = endDate,
                study = study
            )
            study.taskGroupList += newTaskGroup

            taskGroupRepository.save(newTaskGroup)
            studyRepository.save(study)

        }

        println("tasklet 1 complete")
        return RepeatStatus.FINISHED
    }

}