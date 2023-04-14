package com.omnilab.templatekotlin.config.handler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@ControllerAdvice
class CommonExceptionHandler {

    val logger = LoggerFactory.getLogger("CommonExceptionHandler")

    // 404 에러 페이지
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handlesdConflict(req: HttpServletRequest, rep: HttpServletResponse, session: HttpSession, e: NoHandlerFoundException) {
        try {
            rep.sendRedirect("/html/not.html")
        } catch (err: Exception) {
            logger.error("NoHandlerFoundException : {}", err)
        }
    }

    // 잘못된 요청
    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    fun handleException(req: HttpServletRequest, rep: HttpServletResponse, session: HttpSession, e: HttpRequestMethodNotSupportedException) {
        try {
            logger.error("invalid", e)
            rep.sendRedirect("/html/error.html")
        } catch (err: Exception) {
            logger.error("invalid {}", err)
        }
    }

    @ExceptionHandler(value = [RequestRejectedException::class])
    fun rejected(e: RequestRejectedException): String {
        return "redirect:/html/error2.html"
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): String {
        logger.error("error", e)
        return "redirect:/html/error2.html"
    }
}
