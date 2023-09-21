package com.omnilab.templatekotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
//@EnableResourceServer
@EnableJpaAuditing // created_date, last_modified
@SpringBootApplication(exclude = [DataSourceTransactionManagerAutoConfiguration::class, DataSourceAutoConfiguration::class])
@ComponentScan(basePackages = ["com.omnilab.templatekotlin.*"])
class TemplateKotlinApplication

fun main(args: Array<String>) {
	runApplication<TemplateKotlinApplication>(*args)
}
