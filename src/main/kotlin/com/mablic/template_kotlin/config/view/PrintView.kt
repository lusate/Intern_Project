package com.mablic.template_kotlin.config.view

import org.springframework.web.servlet.view.AbstractView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.util.FileCopyUtils
import java.io.FileInputStream
import java.io.File
import java.io.OutputStream
import java.net.URLEncoder
import java.nio.charset.Charset
import java.io.IOException


class PrintView: AbstractView() {

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
            e.printStackTrace()
        }
    }
}