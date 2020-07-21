package com.mablic.template_kotlin.config.handler

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.web.servlet.ModelAndView



class InterceptorHandler: HandlerInterceptorAdapter() {

    @Throws(Exception::class)
    override fun postHandle(req: HttpServletRequest, rep: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(req, rep, handler, modelAndView)
    }

    @Throws(Exception::class)
    override fun afterCompletion(req: HttpServletRequest, rep: HttpServletResponse, handler: Any, ex: Exception?) {
        super.afterCompletion(req, rep, handler, ex)
    }

    @Throws(Exception::class)
    override fun afterConcurrentHandlingStarted(req: HttpServletRequest, rep: HttpServletResponse, handler: Any) {
        super.afterConcurrentHandlingStarted(req, rep, handler)
    }

    @Throws(Exception::class)
    override fun preHandle(req: HttpServletRequest, rep: HttpServletResponse, handler: Any): Boolean {
        //컨트롤러 실행전
        req.characterEncoding = "UTF-8"
        rep.contentType = "text/html; charset=UTF-8"
        return super.preHandle(req, rep, handler)
    }

}