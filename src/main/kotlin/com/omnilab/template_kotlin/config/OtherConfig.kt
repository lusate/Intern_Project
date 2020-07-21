package com.omnilab.template_kotlin.config

import com.omnilab.template_kotlin.common.ImagePaginationRenderer

import org.springframework.context.annotation.Configuration
import org.springframework.integration.config.EnableIntegration

import egovframework.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationRenderer
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer
import egovframework.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationManager

import org.springframework.context.annotation.Bean
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.JavaMailSender

import java.util.*

@Configuration
@EnableIntegration
class OtherConfig {

    // MAIL 설정
    @Bean(name = arrayOf("JavaMailSender"))
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "mail.designerd.co.kr"
        mailSender.port = 587
        mailSender.username = "master@designerd.co.kr"
        mailSender.password = "m@blic_1@3$"
        val javaMailProperties = Properties()
        javaMailProperties.setProperty("mail.transport.protocol", "smtp")
        javaMailProperties.setProperty("mail.smtp.auth", "true")
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true")
        javaMailProperties.setProperty("mail.debug", "true")
        mailSender.javaMailProperties = javaMailProperties
        return mailSender
    }

    //Task Excuetor 설정
    @Bean(name = arrayOf("taskExecutor"))
    fun executor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.setThreadNamePrefix("template-task-")
        executor.keepAliveSeconds = 3600
        executor.corePoolSize = 2
        executor.setQueueCapacity(5)
        executor.maxPoolSize = 10
        executor.setAllowCoreThreadTimeOut(false)
        return executor
    }

    @Bean(name = arrayOf("paginationManager"))
    fun defaultPaginationManager(): DefaultPaginationManager {
        val manager = DefaultPaginationManager()
        val map = HashMap<String, PaginationRenderer>()
        map["image"] = ImagePaginationRenderer()
        map["text"] = DefaultPaginationRenderer()
        manager.setRendererType(map)
        return manager
    }

}