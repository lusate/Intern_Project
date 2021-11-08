package com.omnilab.template_kotlin.config.handler

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.stereotype.Component
import org.springframework.web.context.support.SpringBeanAutowiringSupport
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginSuccessHandler : AuthenticationSuccessHandler {

    private val log = LoggerFactory.getLogger(LoginSuccessHandler::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, auth: Authentication) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this)
        val session = request.session
        val authentication = SecurityContextHolder.getContext().authentication
        val authorites: Iterator<GrantedAuthority> = authentication.authorities.iterator()
        var gName: String? = null
        if (authorites.hasNext()) {
            val g = authorites.next()
            gName = g.authority
        }
        val requestCache: RequestCache = HttpSessionRequestCache()
        val savedRequest = requestCache.getRequest(request, response)
        if (savedRequest != null && savedRequest.toString().endsWith("/index.mi")) {
            val redriectUrl = savedRequest.redirectUrl
            response.sendRedirect(redriectUrl)
        } else {
            response.sendRedirect("/common/home.mi")
        }
    }
}