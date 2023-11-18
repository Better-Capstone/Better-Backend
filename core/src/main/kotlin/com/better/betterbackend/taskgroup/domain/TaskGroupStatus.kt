package com.better.betterbackend.taskgroup.domain

enum class TaskGroupStatus (

    val status: String,

) {

    INPROGRESS("In Progress"),
    END("End"),
    ;

}