package com.omnilab.templatekotlin.config.handler

import com.omnilab.templatekotlin.common.CommonUtils
import com.omnilab.templatekotlin.common.GlobalValues
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationHandler : AuthenticationEntryPoint, AccessDeniedHandler {

    val logger = LoggerFactory.getLogger(this::class.java)

    // 인증 -> 인증이 되지 않은 사용자는 서버에 요청을 보내도 서버에선 요청에 대한 응답을 하지 않는다.
    override fun commence(req: HttpServletRequest, rep: HttpServletResponse, authException: AuthenticationException) {
        logger.error("commence {}, {} | {}", CommonUtils.clientIp(req), req.requestURI, authException.message)
        req.characterEncoding = "UTF-8"
        rep.contentType = "text/html; charset=UTF-8"
        val out = rep.writer
        out.print("<script>")
        out.print("alert('로그인된 사용자만 이용할 수 있습니다.');")
        out.print("location.href='/" + GlobalValues.INDEXED + "';")
        out.print("</script>")
        out.flush()
        out.close()
    }

    // 권한 처리
    override fun handle(
        req: HttpServletRequest,
        rep: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val headerNames: Enumeration<*> = req.getHeaderNames()
        while (headerNames.hasMoreElements()) {
            val key = headerNames.nextElement() as String
            val value: String = req.getHeader(key)
            logger.error("header: $key", value)
        }
        logger.error("handle {} | {}", req.requestURI, accessDeniedException.message)
        req.characterEncoding = "UTF-8"
        rep.contentType = "text/html; charset=UTF-8"
        val out = rep.writer
        when {
            req.requestURI == "/" -> {
                rep.sendRedirect("/${GlobalValues.INDEXED}")
            }

            req.requestURI.contains(".ajax") -> {
                rep.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            }

            else -> {
                out.print("<script>")
                out.print("alert('로그인된 사용자만 이용할 수 있습니다.');")
                out.print("location.href='/';")
                out.print("</script>")
                out.flush()
                out.close()
            }
        }
    }
}

/**
 * AuthenticationEntryPoint : 인증이 되지않은 유저가 요청을 했을때 동작된다.  클라이언트의 credential을 요청하는 HTTP 응답을 보낼 때 사용한다.
 * 클라이언트가 리소스를 요청할 때 미리 이름/비밀번호 같은 credential을 함께 보내면 credential을 요청하는 HTTP 응답을 만들 필요가 없다. 하지만 클라이언트가 접근 권한이 없는 리소스에 인증되지 않은 요청을 보내면
 * AuthenticationEntryPoint 구현체가 클라이언트에 credential을 요청한다.
 *
 * AccessDeniedHandler : 서버에 요청을 할 때 액세스가 가능한지 권한을 체크 후 액세스 할 수 없는 요청을 했을 시 동작된다.
 */