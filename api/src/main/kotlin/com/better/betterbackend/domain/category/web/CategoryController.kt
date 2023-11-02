package com.better.betterbackend.domain.category.web

import com.better.betterbackend.domain.category.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
class CategoryController(

    private val categoryService: CategoryService,

) {

    @PostMapping("/create")
    fun create(@RequestBody name: String): ResponseEntity<Long> {
        return ResponseEntity.ok().body(categoryService.create(name))
    }

}