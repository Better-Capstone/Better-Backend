package com.better.betterbackend.config

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["com.better.betterbackend.batch"])
class BatchDataSourceConfiguration: DefaultBatchConfiguration() {

    @Bean
    @ConfigurationProperties(prefix = "spring.batch-database.datasource")
    fun batchDataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Bean
    fun batchEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("batchDataSource") dataSource: DataSource?
    ): LocalContainerEntityManagerFactoryBean {
        val properties: MutableMap<String, String> = HashMap()
        properties["hibernate.dialect"] = "org.hibernate.dialect.MySQLDialect"
        properties["hibernate.hbm2ddl.auto"] = "update"

        return builder.dataSource(dataSource).packages("com.better.betterbackend.batch")
            .persistenceUnit("batch").properties(properties).build()
    }

    @Bean
    fun batchTransactionManager(@Qualifier("batchEntityManagerFactory") entityManagerFactory: EntityManagerFactory?): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory!!)
    }

//    @Bean
//    fun embeddedDataSource(): DataSource {
//        val embeddedDatabaseBuilder = EmbeddedDatabaseBuilder()
//        return embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.H2)
//            .addScript("/org/springframework/batch/core/schema-h2.sql")
//            .build()
//    }

    override fun getDataSource(): DataSource {
        // return embeddedDataSource()
        return batchDataSource()
    }

    override fun getTransactionManager(): PlatformTransactionManager {
        // return DataSourceTransactionManager(embeddedDataSource())
        return DataSourceTransactionManager(batchDataSource())
    }

}