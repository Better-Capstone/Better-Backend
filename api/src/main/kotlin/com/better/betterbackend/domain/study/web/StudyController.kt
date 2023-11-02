package com.better.betterbackend.domain.study.web

import com.better.betterbackend.domain.study.dto.request.StudyCreateRequestDto
import com.better.betterbackend.domain.study.dto.response.StudyResponseDto
import com.better.betterbackend.domain.study.service.StudyService
import org.springframework.http.ResponseEntity
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
    fun create(@RequestBody request: StudyCreateRequestDto): ResponseEntity<StudyResponseDto> {
        return ResponseEntity.ok().body(studyService.create(request))
    }

}