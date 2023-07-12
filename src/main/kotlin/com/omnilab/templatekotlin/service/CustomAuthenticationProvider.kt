package com.omnilab.templatekotlin.service

import com.omnilab.templatekotlin.common.CommonUtils
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
    private lateinit var request: HttpServletRequest

    @Autowired
    private lateinit var sqlSession: SqlSession

    override fun authenticate(auth: Authentication): Authentication {
        val userId = auth.principal.toString()
        val userPw = auth.credentials.toString()

        var ip = CommonUtils.clientIp(request)
        if (ip == "0:0:0:0:0:0:0:1") {
            ip = "127.0.0.1"
        }
        log.error("CustomAuthenticationProvider~!~!~")

        sqlSession!!.selectOne<Any>("select1")

        val roles = ArrayList<GrantedAuthority>()
        val result = UsernamePasswordAuthenticationToken(userId, "", roles)
        return result
    }

    override fun supports(auth: Class<*>): Boolean {
        return auth == UsernamePasswordAuthenticationToken::class.java
    }
}
