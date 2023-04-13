package com.omnilab.templatekotlin.config.handler

import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

class InterceptorHandler: HandlerInterceptor {

    override fun preHandle(req: HttpServletRequest, rep: HttpServletResponse, handler: Any): Boolean {
        req.characterEncoding = "UTF-8"
        rep.contentType = "text/html; charset=UTF-8"
        return super.preHandle(req, rep, handler)
    }

    override fun postHandle(req: HttpServletRequest, rep: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(req, rep, handler, modelAndView)
    }

    override fun afterCompletion(req: HttpServletRequest, rep: HttpServletResponse, handler: Any, ex: Exception?) {
        super.afterCompletion(req, rep, handler, ex)
    }

}