package com.better.betterbackend.global.batch

import com.better.betterbackend.grouprankhistory.domain.GroupRankHistory
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.taskgroup.dao.TaskGroupRepository
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import com.better.betterbackend.userrank.dao.UserRankRepository
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
                val task =
                    taskList.find { it.member.id == member.id }// task, challenge가 null이 아니고 approve가 일정 퍼센트를 넘었을 경우
                if (task?.challenge != null) {
                    if (0.5 < task.challenge!!.approveMember.size / numOfMember) {//승인멤버가 과반수넘었을때?
                        success = true
                        successCount++
                    }
                }
                if (success) {//todo userrankhistory에는 변화량만 넣어야하는지 아니면 점수를 넣어야하는지 확인 필요 // userrank에 어떻게 값을 넣어야할지모르겟음
                    member.user.userRank.score += 20 //성공했을때 올라가는 점수
                    val userRankHistory = UserRankHistory(
                        uid = member.user.id!!,
                        userRank = member.user.userRank,
                        study = study,
                        score = 20,
                        description = "태스크 인증 완료"
                    )
                    member.user.userRank.userRankHistoryList += userRankHistory
                    userRankRepository.save(member.user.userRank)


                } else {
                    member.kickCount += 1
                    if (member.kickCount == study.kickCondition) {//퇴출조건 만족시 퇴출 + 점수깎기
                        member.memberType = MemberType.WITHDRAW
                        member.user.userRank.score -= (300+study.kickCondition*200)
                    }
                }
            }
            if (successCount == numOfMember) {//전원 태스크 완료시 인원수*5만큼 점수상승
                for (member in study.memberList) {
                    member.user.userRank.score += (5 * numOfMember)
                }
            }

            val period = ChronoUnit.DAYS.between(study.createdAt, date) //그룹랭크 점수 변경
            var totalReward = 0.0
            if (period in 1..182) {
                totalReward = 25 * 0.3 + (successCount / numOfMember) * 70
                study.groupRank.score += totalReward.roundToInt()
            } else if (period in 183..364) {
                totalReward = 50 * 0.3 + (successCount / numOfMember) * 70
                study.groupRank.score += totalReward.roundToInt()
            } else {//1년이상
                totalReward = 100 * 0.3 + (successCount / numOfMember) * 70
                study.groupRank.score += totalReward.roundToInt()
            }
            val groupRankHistory = GroupRankHistory(
                score = totalReward.roundToInt(),
                totalNumber = numOfMember,
                participantsNumber = successCount,
                groupRank = study.groupRank
            )
            study.groupRank.groupRankHistoryList += groupRankHistory

            taskGroup.status = TaskGroupStatus.END
            // 다음 주기의 새로운 TaskGroup 생성
            var endDate = date// 오늘
            if (study.period == Period.EVERYDAY) {
                endDate = endDate.plusDays(1)
            } else if (study.period == Period.WEEKLY) {
                endDate = endDate.plusWeeks(1)
            } else if (study.period == Period.BIWEEKLY) {
                endDate = endDate.plusWeeks(2)
            }
            val newTaskGroup = TaskGroup(
                endDate = endDate,
                study = study
            )
            study.taskGroupList += newTaskGroup


            studyRepository.save(study)

        }
        //현재 바뀐거 userrank.score ,grouprank.score ,member.kickcount ,taskgroup.status


        println("tasklet 1 complete")
        return RepeatStatus.FINISHED

    }
}