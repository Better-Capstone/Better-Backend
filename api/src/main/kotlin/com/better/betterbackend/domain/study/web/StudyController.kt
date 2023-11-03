package com.better.betterbackend.domain.study.web

import com.better.betterbackend.domain.study.dto.request.StudyCreateRequestDto
import com.better.betterbackend.domain.study.dto.response.SimpleStudyResponseDto
import com.better.betterbackend.domain.study.dto.response.StudyResponseDto
import com.better.betterbackend.domain.study.service.StudyService
import org.springframework.http.ResponseEntity
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

    @PostMapping("/create")
    fun create(@RequestBody request: StudyCreateRequestDto): ResponseEntity<SimpleStudyResponseDto> {
        return ResponseEntity.ok().body(studyService.create(request))
    }

    @GetMapping("/{id}")
    fun read(@PathVariable("id") id: Long): ResponseEntity<StudyResponseDto> {
        return ResponseEntity.ok().body(studyService.read(id))
    }

}