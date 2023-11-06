package com.better.betterbackend.study.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

enum class CheckDay (
    @get:JsonValue val checkDay: String,
) {

    EVERYDAY("Everyday"),
    MON("Monday"),
    TUE("Tuesday"),
    WED("Wednesday"),
    THU("Thursday"),
    FRI("Friday"),
    SAT("Saturday"),
    SUN("Sunday"),
    ;

    companion object {
        @JvmStatic
        @JsonCreator
        fun getEnumFromValue(value: String?): CheckDay? {
            return try {
                value?.let { CheckDay.valueOf(it.uppercase(Locale.getDefault())) }
            } catch (e: Exception) {
                null
            }
        }
    }

}