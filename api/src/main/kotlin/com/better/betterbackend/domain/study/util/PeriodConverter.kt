package com.better.betterbackend.domain.study.util

import com.better.betterbackend.study.domain.Period
import org.springframework.core.convert.converter.Converter
import java.util.*

class PeriodConverter: Converter<String, Period> {

    override fun convert(source: String): Period {
        return Period.valueOf(source.uppercase(Locale.getDefault()))
    }

}