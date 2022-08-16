package com.omnilab.template_kotlin.config.handler

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogOutSuccessHandler : LogoutSuccessHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onLogoutSuccess(req: HttpServletRequest, rep: HttpServletResponse, authentication: Authentication) {
        if (authentication.details != null) {
            try {
                req.session.invalidate()
            } catch (e: Exception) {
                log.error("session invalidate Error {} : {}", e.javaClass.name, e.message)
            }
        }
        rep.sendRedirect("/index")
    }
}