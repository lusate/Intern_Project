package com.omnilab.templateKotlin.config.view

import org.slf4j.LoggerFactory
import org.springframework.web.servlet.view.AbstractView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException


class PrintView: AbstractView() {

    val logger = LoggerFactory.getLogger(this::javaClass.name)

    override fun getContentType(): String? {
        return "text/html; charset=UTF-8"
    }

    override fun renderMergedOutputModel(map: MutableMap<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
       response.characterEncoding = "UTF-8"
        try {
            response.writer.use { out ->
                out.println(map["message"])
                out.flush()
            }
        } catch (e: IOException) {
            logger.error("PrintView", e)
        }
    }
}