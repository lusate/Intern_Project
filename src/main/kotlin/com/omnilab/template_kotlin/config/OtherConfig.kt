package com.omnilab.template_kotlin.config

import com.omnilab.template_kotlin.common.ImagePaginationRenderer
import com.omnilab.template_kotlin.config.handler.RestTemplateErrorHandler

import org.springframework.context.annotation.Configuration
import org.springframework.integration.config.EnableIntegration

import egovframework.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationRenderer
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer
import egovframework.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationManager
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.LaxRedirectStrategy
import org.springframework.boot.web.client.RestTemplateBuilder

import org.springframework.context.annotation.Bean
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.client.RestTemplate
import java.time.Duration

import java.util.*

@Configuration
@EnableIntegration
class OtherConfig {

    // MAIL 설정
    @Bean(name = arrayOf("JavaMailSender"))
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = ""
        mailSender.port = 587
        mailSender.username = ""
        mailSender.password = ""
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

    @Bean(name = ["restTemplate"])
    fun restTemplate(): RestTemplate {
        val factorty = HttpComponentsClientHttpRequestFactory()
        factorty.setReadTimeout(5000)
        factorty.setConnectTimeout(3000)
        val httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .setRedirectStrategy(LaxRedirectStrategy())
                .build()
        factorty.httpClient = httpClient

        val restTemplateBuilder = RestTemplateBuilder()
                .requestFactory { factorty }
                .errorHandler(RestTemplateErrorHandler())
                .setConnectTimeout(Duration.ofMinutes(3))
                .build()
        return restTemplateBuilder
    }

}