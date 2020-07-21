package com.mablic.template_kotlin.config.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.context.support.SpringBeanAutowiringSupport

@Component
class LoginSuccessService: AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, auth: Authentication) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this)
        val session = request.session

        val authentication = SecurityContextHolder.getContext().authentication
        val authorites = authentication.authorities.iterator()
        var gName: String? = null
        if (authorites.hasNext()) {
            val g = authorites.next()
            gName = g.authority
        }
        val requestCache = HttpSessionRequestCache()
        val savedRequest = requestCache.getRequest(request, response)

        if (savedRequest != null && savedRequest.toString().endsWith("/index")) {
            val redriectUrl = savedRequest.redirectUrl
            response.sendRedirect(redriectUrl)
        } else {
            response.sendRedirect("/index")
        }
    }
}