package com.omnilab.templateKotlin.service

import com.omnilab.templateKotlin.common.CommonUtils
import org.apache.ibatis.session.SqlSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.ArrayList
import javax.servlet.http.HttpServletRequest

@Component("customAuthenticationProvider")
class CustomAuthenticationProvider : AuthenticationProvider {

    private val log = LoggerFactory.getLogger(CustomAuthenticationProvider::class.java)

    @Autowired(required = true)
    private val request: HttpServletRequest? = null

    @Autowired
    private val sqlSession: SqlSession? = null

    override fun authenticate(auth: Authentication): Authentication {
        val user_id = auth.principal.toString()
        val user_pw = auth.credentials.toString()

        var ip = CommonUtils.clientip(request)
        if (ip == "0:0:0:0:0:0:0:1")
            ip = "127.0.0.1"

        log.error("CustomAuthenticationProvider~!~!~")

        sqlSession!!.selectOne<Any>("select1")

        val roles = ArrayList<GrantedAuthority>()
        val result = UsernamePasswordAuthenticationToken(user_id, "", roles)
        return result

    }

    override fun supports(auth: Class<*>): Boolean {
        return auth == UsernamePasswordAuthenticationToken::class.java
    }

}