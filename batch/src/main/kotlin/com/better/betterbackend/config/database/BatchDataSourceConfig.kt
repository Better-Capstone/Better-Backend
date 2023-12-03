package com.better.betterbackend.config.database

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["com.better.betterbackend.batch"],
    entityManagerFactoryRef = "batchEntityManager",
    transactionManagerRef = "batchTransactionManager"
)
class BatchDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.batch-database.datasource")
    fun batchDataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Bean
    fun batchEntityManager(): LocalContainerEntityManagerFactoryBean {
        val entityManager = LocalContainerEntityManagerFactoryBean()
        entityManager.dataSource = batchDataSource()
        entityManager.setPackagesToScan("com.better.betterbackend.batch")
        entityManager.jpaVendorAdapter = HibernateJpaVendorAdapter()

        return entityManager
    }

    @Bean
    fun batchTransactionManager(): PlatformTransactionManager {
        return JpaTransactionManager(batchEntityManager().`object`!!)
    }

}