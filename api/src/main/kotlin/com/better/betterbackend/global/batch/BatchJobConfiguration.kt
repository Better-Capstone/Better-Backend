package com.better.betterbackend.global.batch

import com.better.betterbackend.study.domain.Period
import com.better.betterbackend.taskgroup.dao.TaskGroupRepository
import com.better.betterbackend.taskgroup.domain.TaskGroup
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDate

import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

class CustomTasklet (private val taskGroupRepository : TaskGroupRepository) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val date = LocalDate.now()
        val taskGroupList = taskGroupRepository.findAllByStatus(TaskGroupStatus.INPROGRESS)
            .filter { it.endDate == date }
        val newTaskGroupList = arrayListOf<TaskGroup>()

        for (taskGroup in taskGroupList) {//어차피 지금 이 태스크 그룹은 같은 스터디니까?
            val study = taskGroup.study!!
            val taskList = taskGroup.taskList
            val numOfMember = taskGroup.study!!.numOfMember

            //val userRankList = ArrayList<UserRank>()
            var successCount = 0
            for (member in study.memberList) {

                var success = false
                val task = taskList.find { it.member.id == member.id }// task, challenge가 null이 아니고 approve가 일정 퍼센트를 넘었을 경우
                if(task?.challenge != null ){
                    if(0.5< task!!.challenge!!.approveMember.size/numOfMember){//승인멤버가 과반수넘었을때?
                        success = true
                        successCount++
                    }
                }
//                val userRank = member.user.userRank //굳이 userRank만들어야하는지?
                if(success){//todo userrankhistory에는 변화량만 넣어야하는지 아니면 점수를 넣어야하는지 확인 필요 // userrank에 어떻게 값을 넣어야할지모르겟음
                    member.user.userRank.score += 20 //성공했을때 올라가는 점수

                }
                else{//todo 실패했을시에 점수변동은 없고 kickcount가 올라갔던거같은데 확인필요
                    member.kickCount += 1
                    //todo member.kickCount =>study.kickCount 이면 멤버 퇴출? 아니면 이미 구현돼있는지 확인 필요
                }
            }
            if(successCount == numOfMember){//전원 태스크 완료시 인원수*5만큼 점수상승
                for (member in study.memberList) {
                    member.user.userRank.score += (5*numOfMember)
                }
            }
            val period = ChronoUnit.DAYS.between(study.createdAt,date) //그룹랭크 점수 변경
            if (period in 1..182){
                var totalReward = 25*0.3+(successCount/numOfMember)*70  //grouprank의 디폴트는 0?
                study.groupRank.score+=totalReward.roundToInt()
            }
            else if(period in 183..364){
                var totalReward = 50*0.3+(successCount/numOfMember)*70
                study.groupRank.score+=totalReward.roundToInt()
            }
            else{//1년이상
                var totalReward = 100*0.3+(successCount/numOfMember)*70
                study.groupRank.score+=totalReward.roundToInt()
            }//todo groupRankHistory 어떻게 넣을지 상의
            taskGroup.status = TaskGroupStatus.END
            // 다음 주기의 새로운 TaskGroup 생성
            var endDate = date// 오늘
            if(study.period==Period.EVERYDAY){
                endDate = endDate.plusDays(1)
            }
            else if(study.period==Period.WEEKLY){
                endDate = endDate.plusWeeks(1)
            }
            else if(study.period==Period.BIWEEKLY){
                endDate = endDate.plusWeeks(2)
            }
            val newTaskGroup = TaskGroup(
                endDate = endDate,
                study = study
            )
            newTaskGroupList.add(newTaskGroup)

        }
        //현재 바뀐거 userrank.score ,grouprank.score ,member.kickcount ,taskgroup.status


        println("tasklet 1 complete")
        return RepeatStatus.FINISHED

    }
}





@Configuration
class HelloJobConfiguration(
    private val taskGroupRepository: TaskGroupRepository,
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager
) {
    @Bean
    fun singleStepJob(): Job {
        return JobBuilder("singleStepJob", jobRepository)
            .start(singleStep())
            .build()
    }

    fun singleStep(): Step {
        return StepBuilder("singleStep", jobRepository)
            .tasklet(CustomTasklet(taskGroupRepository), transactionManager)
            .build()
    }
}








