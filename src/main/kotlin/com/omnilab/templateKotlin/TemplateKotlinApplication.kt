package com.omnilab.templateKotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
@SpringBootApplication(exclude = [DataSourceTransactionManagerAutoConfiguration::class, DataSourceAutoConfiguration::class])
@ComponentScan(basePackages = ["com.omnilab.templateKotlin.*"])
open class TemplateKotlinApplication

fun main(args: Array<String>) {
	runApplication<TemplateKotlinApplication>(*args)
}
