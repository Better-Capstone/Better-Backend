package com.better.betterbackend.batch.tasklet

import com.better.betterbackend.member.dao.MemberRepository
import com.better.betterbackend.member.domain.Member
import com.better.betterbackend.member.domain.MemberType
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.study.domain.StudyStatus
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class SecondTasklet(

    private val memberRepository: MemberRepository,

    private val studyRepository: StudyRepository,

): Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val kickedMember = memberRepository.findAll().filter {
            it.memberType != MemberType.WITHDRAW && (it.kickCount == it.study!!.kickCondition || it.user.userRank.score < it.study!!.minRank)
        }

        val newMemberList = arrayListOf<Member>()
        val newStudyList = arrayListOf<Study>()

        for (member in kickedMember) {
            member.kickCount = 0
            val study = member.study!!
            if (member.memberType == MemberType.OWNER) {
                val memberList = study.memberList.filter { it.id!! != member.id!! && it.memberType != MemberType.WITHDRAW }

                if (memberList.isEmpty()) {
                    study.status = StudyStatus.END
                    study.taskGroupList.find { it.status == TaskGroupStatus.INPROGRESS }!!.status = TaskGroupStatus.END
                } else {
                    val newOwner = memberList.minBy { it.createdAt }

                    study.owner = newOwner.user
                    newOwner.memberType = MemberType.OWNER
                    newMemberList += newOwner
                }
            }

            member.memberType = MemberType.WITHDRAW
            study.numOfMember--
            newMemberList += member

            if (newStudyList.find { it.id!! == study.id!! } == null) {
                newStudyList += study
            }
        }

        memberRepository.saveAll(newMemberList)
        studyRepository.saveAll(newStudyList)

        return RepeatStatus.FINISHED
    }

}