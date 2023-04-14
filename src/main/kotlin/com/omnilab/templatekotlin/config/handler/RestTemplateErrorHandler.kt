package com.omnilab.templatekotlin.config.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

class RestTemplateErrorHandler : ResponseErrorHandler {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun hasError(response: ClientHttpResponse): Boolean {
        return false
    }

    // hasError에서 true를 return하면 해당 메서드 실행.
    override fun handleError(response: ClientHttpResponse) {
        logger.error("{}", response.statusCode)
    }
}
