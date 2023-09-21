package com.omnilab.templatekotlin.config.handler

import com.omnilab.templatekotlin.controller.SessionConst
import com.omnilab.templatekotlin.domain.member.Member
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.stereotype.Component
import org.springframework.web.context.support.SpringBeanAutowiringSupport
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginSuccessHandler : AuthenticationSuccessHandler {

    private val log = LoggerFactory.getLogger(LoginSuccessHandler::class.java)

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, auth: Authentication) {
        val session = request.session
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this)
        val authentication = SecurityContextHolder.getContext().authentication

        val userDto: Member = authentication.details as Member
        session.setAttribute(SessionConst.LOGIN_MEMBER, userDto.id)

        log.error("{}, {}", userDto.loginId, userDto.id)

        val authorites: Iterator<GrantedAuthority> = authentication.authorities.iterator()
        var gName: String? = null
        if (authorites.hasNext()) {
            val g = authorites.next()
            gName = g.authority
        }
        val requestCache: RequestCache = HttpSessionRequestCache()
        val savedRequest = requestCache.getRequest(request, response)
        if (savedRequest != null && savedRequest.toString().endsWith("/index.mi")) {
            val redirectUrl = savedRequest.redirectUrl
            response.sendRedirect(redirectUrl)
        }
        else {
            response.sendRedirect("/")
        }
    }
}