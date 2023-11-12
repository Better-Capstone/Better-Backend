package com.better.betterbackend.domain.category.web

import com.better.betterbackend.domain.category.dto.CategoryDto
import com.better.betterbackend.domain.category.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController(

    private val categoryService: CategoryService,

) {

    @Operation(summary = "카테고리 생성(테스트 용)")
    @PostMapping("/create")
    fun create(@RequestBody name: String): ResponseEntity<Long> {
        return ResponseEntity.ok().body(categoryService.create(name))
    }

    @Operation(summary = "카테고리 아이디 조회(테스트 용)")
    @GetMapping("/read/{id}")
    fun read(@PathVariable("id") id: Long): ResponseEntity<CategoryDto> {
        return ResponseEntity.ok().body(categoryService.read(id))
    }

}