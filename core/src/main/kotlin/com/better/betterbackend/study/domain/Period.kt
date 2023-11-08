package com.better.betterbackend.study.domain

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

enum class Period (

    val period: String,

) {

    EVERYDAY("Everyday"),
    WEEKLY("Weekly"),
    BIWEEKLY("Biweekly"),
    ;

    companion object {
        @JvmStatic
        @JsonCreator
        fun getEnumFromValue(value: String?): Period? {
            return try {
                value?.let { Period.valueOf(it.uppercase(Locale.getDefault())) }
            } catch (e: Exception) {
                null
            }
        }
    }

}