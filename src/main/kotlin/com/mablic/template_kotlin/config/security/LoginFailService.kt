package com.mablic.template_kotlin.config.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginFailService: AuthenticationFailureHandler {

    override fun onAuthenticationFailure(req: HttpServletRequest, rep: HttpServletResponse, auth: AuthenticationException) {
        req.setCharacterEncoding("UTF-8")
        rep.setContentType("text/html; charset=UTF-8")
        val out = rep.getWriter()

        if (auth.getLocalizedMessage().equals("BAD")) {
            if (req.getParameter("gubun") != null && req.getParameter("gubun").equals("re")) {
                out.print("<script>alert('비밀 번호가 올바르지 않습니다.');</script>")
                out.flush()
                out.close()
            } else {
                out.print("<script>alert('로그인 정보가 올바르지 않습니다.'); location.href = '/index.service'</script>")
                out.flush()
                out.close()
            }
        } else {
            out.print("<script>alert('" + auth.getLocalizedMessage() + "'); location.href = '/index.service'</script>")
            out.flush()
            out.close()
        }
    }
}