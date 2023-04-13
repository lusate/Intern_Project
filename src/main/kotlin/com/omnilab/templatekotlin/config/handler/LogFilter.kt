package com.omnilab.templatekotlin.config.handler

import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.security.SecureRandom
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogFilter : OncePerRequestFilter() {
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, filterChain: FilterChain) {
        MDC.put("REQSEQ", RandomStringUtils.random(10, 0, 0, true, false, null, SecureRandom()))
        filterChain.doFilter(req, res)
        MDC.clear()
    }
}