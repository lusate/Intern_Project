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
        /*
        if(gName.equals("ROLE_CERTIFI")){
            String ip = CommonUtils.clientip(request);
            if(ip.equals("0:0:0:0:0:0:0:1"))
                ip="127.0.0.1";
            LoginInfoDto loginInfoDto = (LoginInfoDto) auth.getDetails();

            session.setAttribute("CE_USR_SEQ", loginInfoDto.getUsr_seq());
            session.setAttribute("CE_USR_EMAIL", loginInfoDto.getUsr_email());
            session.setAttribute("CE_USR_GRP_CD", loginInfoDto.getUsr_grp_cd());
            session.setAttribute("CE_USR_ID", loginInfoDto.getUsr_id());
            session.setAttribute("CE_USR_NAME", loginInfoDto.getUsr_name());
            session.setAttribute("CE_USR_IP", ip);

            IpSendDto ipSendDto = new IpSendDto();
            ipSendDto.setUsr_seq(loginInfoDto.getUsr_seq());
            ipSendDto.setUsr_email(loginInfoDto.getUsr_email());
            ipSendDto.setUsr_ip(ip);

            try{
                userManageService.createCertificationNo(ipSendDto);
                response.sendRedirect("/certification.mi");
            }catch (Exception e) {
                logger.error("[" + CommonUtils.getDateTimeNow() + "]", e);
            }
        }else{
            String ip = CommonUtils.clientip(request);
            if(ip.equals("0:0:0:0:0:0:0:1")) { ip="127.0.0.1"; }
            LoginInfoDto loginInfoDto = (LoginInfoDto) auth.getDetails();
            session.setAttribute("SE_USR_SEQ", loginInfoDto.getUsr_seq());
            session.setAttribute("SE_USR_GRP", loginInfoDto.getUsr_grp());
            session.setAttribute("SE_USR_GRP_CD", loginInfoDto.getUsr_grp_cd());
            session.setAttribute("SE_USR_ID", loginInfoDto.getUsr_id());
            session.setAttribute("SE_USR_EMAIL", loginInfoDto.getUsr_email());
            session.setAttribute("SE_USR_NAME", loginInfoDto.getUsr_name());
            session.setAttribute("SE_USR_MOBILE", loginInfoDto.getUsr_mobile());
            session.setAttribute("SE_USR_STAT", loginInfoDto.getUsr_stat());
            session.setAttribute("SE_USR_AUTH_CD", loginInfoDto.getUsr_auth_cd());
            session.setAttribute("SE_USR_IP", ip);

            session.removeAttribute(loginInfoDto.getUsr_email());
            session.setAttribute(loginInfoDto.getUsr_email(), HttpSessionBindingListenerImpl.getInstance());

            if(HttpSessionBindingListenerImpl.getInstance().isDuple()){
                DuplicateBroadcastThread dbt = new DuplicateBroadcastThread();
                dbt.setNotifyDao(notifyDao);
                dbt.start();
            }

            RequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(request, response);

            if(savedRequest!=null && savedRequest.toString().endsWith("/index.mi")){
                String redriectUrl = savedRequest.getRedirectUrl();
                response.sendRedirect(redriectUrl);
            }else{
                response.sendRedirect("/common/home.mi");
            }
        }
        */
    }
}