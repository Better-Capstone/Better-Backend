package com.better.betterbackend.category.domain

import com.better.betterbackend.study.domain.Study
import com.better.betterbackend.model.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Category (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String,

    @OneToMany(mappedBy = "category", cascade = [CascadeType.REMOVE])
    val studyList: List<Study> = ArrayList(),

): BaseTimeEntity() {

}