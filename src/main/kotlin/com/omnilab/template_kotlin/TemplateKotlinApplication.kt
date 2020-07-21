package com.omnilab.template_kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(exclude = arrayOf(DataSourceTransactionManagerAutoConfiguration::class, DataSourceAutoConfiguration::class))
@ComponentScan(basePackages = arrayOf("com.omnilab.template_kotlin.*"))
open class TemplateKotlinApplication

fun main(args: Array<String>) {
	runApplication<TemplateKotlinApplication>(*args)
}
