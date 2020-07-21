package com.omnilab.template_kotlin.config.security

import com.omnilab.template_kotlin.common.CommonUtils
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.GrantedAuthority
import java.util.ArrayList

@Component("CustomAuthenticationProvider")
class CustomAuthenticationProvider: AuthenticationProvider{

    private val logger = LoggerFactory.getLogger(CustomAuthenticationProvider::class.java)

    @Autowired(required = true)
    private val request: HttpServletRequest? = null
/*
    @Autowired
    private val sqlSession: SqlSession? = null
*/
    override fun authenticate(auth: Authentication): Authentication {
        val user_email = auth.principal.toString()
        val user_pw = auth.credentials.toString()
        var ip = CommonUtils.clientip(request)
        if (ip == "0:0:0:0:0:0:0:1")
            ip = "127.0.0.1"

        val roles = ArrayList<GrantedAuthority>()
        val result = UsernamePasswordAuthenticationToken(user_email, user_pw, roles)
        return result
    }


    override fun supports(auth: Class<*>): Boolean {
        return auth.equals(UsernamePasswordAuthenticationToken::class.java)
    }
}