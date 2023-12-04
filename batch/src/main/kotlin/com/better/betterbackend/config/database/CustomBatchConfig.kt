package com.better.betterbackend.config.database

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class CustomBatchConfig(

    @Qualifier("batchDataSource")
    val batchDataSource: DataSource

): DefaultBatchConfiguration() {

    override fun getDataSource(): DataSource {
        return batchDataSource
    }

    override fun getTransactionManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(batchDataSource)
    }

}