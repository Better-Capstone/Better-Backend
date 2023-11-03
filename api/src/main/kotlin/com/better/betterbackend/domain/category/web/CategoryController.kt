package com.better.betterbackend.domain.category.web

import com.better.betterbackend.domain.category.dto.CategoryResponseDto
import com.better.betterbackend.domain.category.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController(

    private val categoryService: CategoryService,

) {

    @PostMapping("/create")
    fun create(@RequestBody name: String): ResponseEntity<Long> {
        return ResponseEntity.ok().body(categoryService.create(name))
    }

    @GetMapping("/read/{id}")
    fun read(@PathVariable("id") id: Long): ResponseEntity<CategoryResponseDto> {
        return ResponseEntity.ok().body(categoryService.read(id))
    }

}