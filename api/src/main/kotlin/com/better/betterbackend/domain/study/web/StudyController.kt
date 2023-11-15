package com.better.betterbackend.domain.study.web

import com.better.betterbackend.category.domain.Category
import com.better.betterbackend.domain.grouprank.dto.GroupRankDto
import com.better.betterbackend.domain.grouprankhistory.dto.GroupRankHistoryDto
import com.better.betterbackend.domain.study.dto.request.StudyCreateRequestDto
import com.better.betterbackend.domain.study.dto.StudyDto
import com.better.betterbackend.domain.study.service.StudyService
import com.better.betterbackend.domain.user.dto.UserDto
import com.better.betterbackend.global.validation.ValidationSequence
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/study")
class StudyController(

    private val studyService: StudyService,

) {

    @Operation(summary = "스터디 생성")
    @PostMapping("/create")
    fun create(
        @RequestBody @Validated(value = [ValidationSequence::class]) request: StudyCreateRequestDto
    ): ResponseEntity<StudyDto> {
        return ResponseEntity.ok().body(studyService.create(request))
    }

    @Operation(summary = "아이디로 스터디 조회")
    @GetMapping("/{id}")
    fun getStudyById(@PathVariable("id") id: Long): ResponseEntity<StudyDto> {
        return ResponseEntity.ok().body(studyService.getStudyById(id))
    }

    @Operation(summary = "카테고리로 스터디 조회")
    @GetMapping("/category/{id}")
    fun getStudyByCategory(@PathVariable("id") categoryId: Long): ResponseEntity<List<StudyDto>> {
        return ResponseEntity.ok().body(studyService.getStudyByCategory(categoryId))
    }

    @Operation(summary = "유저로 스터디 조회")
    @GetMapping("/user/{id}")
    fun getStudyByUser(@PathVariable("id") userId: Long): ResponseEntity<List<StudyDto>> {
        return ResponseEntity.ok().body(studyService.getStudyByUser(userId))
    }

    @Operation(summary = "진행 중인 스터디 조회")
    @GetMapping
    fun getInProgressStudies(): ResponseEntity<List<StudyDto>> {
        return ResponseEntity.ok().body(studyService.getInProgressStudies())
    }

    @Operation(summary = "스터디 가입")
    @PostMapping("/{id}/join")
    fun joinStudy(@PathVariable("id") studyId: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(studyService.joinStudy(studyId))
    }

    @Operation(summary = "스터디 가입 유저 조회")
    @GetMapping("/{id}/users")
    fun getUsersInStudy(@PathVariable("id") studyId: Long): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok().body(studyService.getUsersInStudy(studyId))
    }

    @Operation(summary = "카테고리 아이디 & 키워드로 스터디 서치")
    @GetMapping("/search")
    fun getStudyByKeywordAndCategory(
        @RequestParam("categoryId") categoryId: Long?, @RequestParam("keyword") keyword: String?
    ): ResponseEntity<List<StudyDto>> {
        return ResponseEntity.ok().body(studyService.getStudyByKeywordAndCategory(categoryId, keyword))
    }

    @Operation(summary = "스터디 랭크 조회")
    @GetMapping("/{id}/rank")
    fun getGroupRank(@PathVariable("id") studyId: Long): ResponseEntity<GroupRankDto> {
        return ResponseEntity.ok().body(studyService.getGroupRank(studyId))
    }

    @Operation(summary = "스터디 랭크 기록 조회")
    @GetMapping("/{id}/report/history")
    fun getGroupRankHistory(@PathVariable("id") studyId: Long): ResponseEntity<List<GroupRankHistoryDto>> {
        return ResponseEntity.ok().body(studyService.getGroupRankHistory(studyId))
    }

}