package com.better.betterbackend.domain.study.web

import com.better.betterbackend.domain.grouprankhistory.dto.response.GroupRankHistoryResponseDto
import com.better.betterbackend.domain.study.dto.request.StudyCreateRequestDto
import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto
import com.better.betterbackend.domain.study.dto.response.StudyResponseDto
import com.better.betterbackend.domain.study.service.StudyService
import com.better.betterbackend.global.validation.ValidationSequence
import com.better.betterbackend.member.domain.MemberType
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/study")
class StudyController(

    private val studyService: StudyService,

) {

    @Operation(summary = "스터디 생성")
    @PostMapping("/create")
    fun create(@RequestBody @Validated(value = [ValidationSequence::class]) request: StudyCreateRequestDto): ResponseEntity<SimpleStudyResponseDto> {
        return ResponseEntity.ok().body(studyService.create(request))
    }

    @Operation(summary = "아이디로 스터디 조회")
    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): ResponseEntity<StudyResponseDto> {
        return ResponseEntity.ok().body(studyService.readById(id))
    }

    @Operation(summary = "카테고리로 스터디 조회")
    @GetMapping("/category/{id}")
    fun getByCategory(@PathVariable("id") categoryId: Long): ResponseEntity<List<StudyResponseDto>> {
        return ResponseEntity.ok().body(studyService.readByCategory(categoryId))
    }

    @Operation(summary = "유저로 스터디 조회")
    @GetMapping("/user/{id}")
    fun getByUser(@PathVariable("id") userId: Long): ResponseEntity<List<StudyResponseDto>> {
        return ResponseEntity.ok().body(studyService.readByUser(userId))
    }

    @Operation(summary = "진행 중인 스터디 조회")
    @GetMapping
    fun getInProgressedStudies(): ResponseEntity<List<StudyResponseDto>> {
        return ResponseEntity.ok().body(studyService.readInProgressStudies())
    }

    @Operation(summary = "스터디 가입")
    @PostMapping("/{id}/join")
    fun joinStudy(@PathVariable("id") studyId: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(studyService.joinStudy(studyId, MemberType.MEMBER))
    }

    @Operation(summary = "스터디 랭크 기록 조회")
    @GetMapping("/{id}/report/history")
    fun getGroupRankHistory(@PathVariable("id") studyId: Long): ResponseEntity<List<GroupRankHistoryResponseDto>> {
        return ResponseEntity.ok().body(studyService.getGroupRankHistory(studyId))
    }

}