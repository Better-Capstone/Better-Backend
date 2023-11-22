package com.better.betterbackend.domain.task.service

import com.better.betterbackend.challenge.dao.ChallengeRepository
import com.better.betterbackend.challenge.domain.Challenge
import com.better.betterbackend.domain.task.dto.TaskDto
import com.better.betterbackend.domain.task.dto.request.TaskRegisterRequestDto
import com.better.betterbackend.global.exception.CustomException
import com.better.betterbackend.global.exception.ErrorCode
import com.better.betterbackend.study.dao.StudyRepository
import com.better.betterbackend.task.dao.TaskRepository
import com.better.betterbackend.task.domain.Task
import com.better.betterbackend.taskgroup.domain.TaskGroupStatus
import com.better.betterbackend.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class TaskService (

    private val taskRepository : TaskRepository,

    private val studyRepository : StudyRepository,
    private val challengeRepository: ChallengeRepository,

){

    fun register(request: TaskRegisterRequestDto): TaskDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val user = (principal as UserDetails) as User

        val studyId = request.studyId
        val study = studyRepository.findByIdOrNull(studyId) ?: throw CustomException(ErrorCode.STUDY_NOT_FOUND)

        // 해당 스터디에 참여하지 않은 유저인 경우
        val member = study.memberList.find { it.user.id!! == user.id!! } ?: throw CustomException(ErrorCode.NOT_PARTICIPATED)

        val taskGroup = study.taskGroupList.find { it.status == TaskGroupStatus.INPROGRESS }!!

        val task = Task(
            title = request.title,
            member = member,
            taskGroup = taskGroup,
        )
        taskRepository.save(task)

        return TaskDto(task)
    }

    fun test(){
        val study1 = studyRepository.findByIdOrNull(1)
        val study2 = studyRepository.findByIdOrNull(2)
        val taskGroup1 = study1!!.taskGroupList.find { it.status == TaskGroupStatus.INPROGRESS }!!
        val taskGroup2 = study2!!.taskGroupList.find { it.status == TaskGroupStatus.INPROGRESS }!!
        val member1 = study1!!.memberList.find { it.id?.toInt() == 1 }
        val member2 = study1!!.memberList.find { it.id?.toInt() == 3 }
        val member3 = study1!!.memberList.find { it.id?.toInt() == 4 }
        val member4 = study1!!.memberList.find { it.id?.toInt() == 5 }
        val member5 = study1!!.memberList.find { it.id?.toInt() == 6 }
        val member6 = study2!!.memberList.find { it.id?.toInt() == 2 }
        val member7 = study2!!.memberList.find { it.id?.toInt() == 7 }
        val member8 = study2!!.memberList.find { it.id?.toInt() == 8 }
        val member9 = study2!!.memberList.find { it.id?.toInt() == 9 }
        val member10 = study2!!.memberList.find { it.id?.toInt() == 10 }
        val task1 = Task(
            taskGroup = taskGroup1,
            member = member1!!,
            title = "task1",
            challenge = null,
        )
        taskRepository.save(task1)
        val task2 = Task(
            taskGroup = taskGroup1,
            member = member2!!,
            title = "task2",
            challenge = null,
        )
        taskRepository.save(task2)
        val task3 = Task(
            taskGroup = taskGroup1,
            member = member3!!,
            title = "task3",
            challenge = null,
        )
        taskRepository.save(task3)
        val task4 = Task(
            taskGroup = taskGroup1,
            member = member4!!,
            title = "task4",
            challenge = null,
        )
        taskRepository.save(task4)
        val task5 = Task(
            taskGroup = taskGroup1,
            member = member5!!,
            title = "task5",
            challenge = null,
        )
        taskRepository.save(task5)
        val task6 = Task(
            taskGroup = taskGroup2,
            member = member6!!,
            title = "task6",
            challenge = null,
        )
        taskRepository.save(task6)
        val task7 = Task(
            taskGroup = taskGroup2,
            member = member7!!,
            title = "task7",
            challenge = null,
        )
        taskRepository.save(task7)
        val task8 = Task(
            taskGroup = taskGroup2,
            member = member8!!,
            title = "task8",
            challenge = null,
        )
        taskRepository.save(task8)
        val task9 = Task(
            taskGroup = taskGroup2,
            member = member9!!,
            title = "task9",
            challenge = null,
        )
        taskRepository.save(task9)
        val task10 = Task(
            taskGroup = taskGroup2,
            member = member10!!,
            title = "task10",
            challenge = null,
        )
        taskRepository.save(task10)
        val a1 = ArrayList<Long>()
        a1+=2
        a1+=3
        a1+=4
        a1+=5

        val challenge1 = Challenge(
            task = task1,
            description = "description1",
            image = "image1",
            approveMember = a1,
            rejectMember = ArrayList(),
        )
        task1.challenge = challenge1
        challengeRepository.save(challenge1)


        val a2 = ArrayList<Long>()
        a2+=3
        a2+=4
        a2+=5
        val challenge2 = Challenge(
            task = task2,
            description = "description2",
            image = "image2",
            approveMember = a2,
            rejectMember = ArrayList(),
        )
        task2.challenge = challenge2
        challengeRepository.save(challenge2)

        val a3 = ArrayList<Long>()

        a3+=4
        a3+=5
        val challenge3 = Challenge(
            task = task3,
            description = "description3",
            image = "image3",
            approveMember = a3,
            rejectMember = ArrayList(),
        )
        task3.challenge = challenge3
        challengeRepository.save(challenge3)

        val a4 = ArrayList<Long>()


        a4+=5
        val challenge4 = Challenge(
            task = task4,
            description = "description4",
            image = "image4",
            approveMember = a4,
            rejectMember = ArrayList(),
        )
        task4.challenge = challenge4
        challengeRepository.save(challenge4)

        val a5 = ArrayList<Long>()
        val challenge5 = Challenge(
            task = task5,
            description = "description5",
            image = "image5",
            approveMember = a5,
            rejectMember = ArrayList(),
        )
        task5.challenge = challenge5
        challengeRepository.save(challenge5)

        val a6 = ArrayList<Long>()
        a6+=7
        a6+=8
        a6+=9
        val challenge6 = Challenge(
            task = task6,
            description = "description6",
            image = "image6",
            approveMember = a6,
            rejectMember = ArrayList(),
        )
        task6.challenge = challenge6
        challengeRepository.save(challenge6)

        val a7 = ArrayList<Long>()
        a7+=7
        a7+=8
        a7+=9
        val challenge7 = Challenge(
            task = task7,
            description = "description7",
            image = "image7",
            approveMember = a7,
            rejectMember = ArrayList(),
        )
        task7.challenge = challenge7
        challengeRepository.save(challenge7)

        val a8 = ArrayList<Long>()
        a8+=6
        a8+=8
        a8+=9
        val challenge8 = Challenge(
            task = task8,
            description = "description8",
            image = "image8",
            approveMember = a8,
            rejectMember = ArrayList(),
        )
        task8.challenge = challenge8
        challengeRepository.save(challenge8)
        val a9 = ArrayList<Long>()
        a9+=6
        a9+=7
        a9+=9

        val challenge9 = Challenge(
            task = task9,
            description = "description9",
            image = "image9",
            approveMember = a9,
            rejectMember = ArrayList(),
        )
        task9.challenge = challenge9
        challengeRepository.save(challenge9)
        val a10 = ArrayList<Long>()
        a10+=6
        a10+=7
        a10+=8

        val challenge10 = Challenge(
            task = task10,
            description = "description10",
            image = "image10",
            approveMember = a10,
            rejectMember = ArrayList(),
        )
        task10.challenge = challenge10
        challengeRepository.save(challenge10)





    }


}