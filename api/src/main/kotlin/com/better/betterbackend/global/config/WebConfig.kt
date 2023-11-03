package com.better.betterbackend.global.config

import com.better.betterbackend.domain.study.util.CheckDayConverter
import com.better.betterbackend.domain.study.util.PeriodConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(CheckDayConverter())
        registry.addConverter(PeriodConverter())
    }

}