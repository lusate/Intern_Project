package com.omnilab.templateKotlin.config.handler

import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

class RestTemplateErrorHandler: ResponseErrorHandler  {

    override fun hasError(response: ClientHttpResponse): Boolean {
        return false
    }

    // hasError에서 true를 return하면 해당 메서드 실행.
    override fun handleError(response: ClientHttpResponse) {

    }

}