package com.better.betterbackend.task.domain

enum class TaskStatus (

    val status: String,

) {

    INPROGRESS("In Progress"),
    END("End"),
    ;

}