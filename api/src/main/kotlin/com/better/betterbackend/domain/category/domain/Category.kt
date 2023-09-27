package com.better.betterbackend.domain.category.domain

import com.better.betterbackend.domain.study.domain.Study
import com.better.betterbackend.global.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Category (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String,

    @OneToMany(mappedBy = "category", cascade = [CascadeType.REMOVE])
    val studyList: List<Study>,

): BaseTimeEntity() {
}