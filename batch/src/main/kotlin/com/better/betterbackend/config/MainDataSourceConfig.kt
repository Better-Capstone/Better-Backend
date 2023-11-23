package com.better.betterbackend.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
    basePackages = ["com.better.betterbackend"],
    entityManagerFactoryRef = "mainEntityManager",
    transactionManagerRef = "mainTransactionManager"
)
class MainDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.main-database.datasource")
    fun mainDataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Primary
    @Bean
    fun mainEntityManager(): LocalContainerEntityManagerFactoryBean {
        val entityManager = LocalContainerEntityManagerFactoryBean()
        entityManager.dataSource = mainDataSource()
        entityManager.setPackagesToScan("com.better.betterbackend")
        entityManager.jpaVendorAdapter = HibernateJpaVendorAdapter()

        return entityManager
    }

    @Primary
    @Bean
    fun mainTransactionManager(): PlatformTransactionManager {
        return JpaTransactionManager(mainEntityManager().`object`!!)
    }

}