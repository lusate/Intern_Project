package com.omnilab.templatekotlin.service

import com.omnilab.templatekotlin.common.CommonUtils
import org.apache.ibatis.session.SqlSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component("customAuthenticationProvider")
class CustomAuthenticationProvider(private val userDetailsService: UserDetailsService
) : AuthenticationProvider {


    @Autowired
    private val memberService: MemberService? = null

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

//        val passwordEncoder: PasswordEncoder =   //null
        val token: UsernamePasswordAuthenticationToken


        // 유저 정보조회
        val user = memberService!!.findUserByLoginId(userId)

        if(user.isEmpty){
            throw BadCredentialsException("아이디 비번 안 맞음")
        }else{
            val userDto = user.get()
            log.error("log : {}", userDto.toString())
            val passwordEncoder: PasswordEncoder = memberService.passwordEncoder()

            if (passwordEncoder.matches(userPw, userDto.password)) {
                // 일치하는 user 정보가 있는지 확인
                val roles: MutableList<GrantedAuthority> = ArrayList()
//            val roles = ArrayList<GrantedAuthority>()
                roles.add(SimpleGrantedAuthority("USER"))


                // USER, ADMIN 역할을 주는데 ArrayList()로 비어있었기 때문에 주지 못한 것
                token = UsernamePasswordAuthenticationToken(user.get().loginId, null, user.get().authorities)
                token.details = userDto
                // 인증된 user 정보를 담아 SecurityContextHolder에 저장되는 token
                return token
            }else{
                throw BadCredentialsException("아이디 비번 안 맞음")
            }

        }

    }




    override fun supports(auth: Class<*>): Boolean {
        return auth == UsernamePasswordAuthenticationToken::class.java
    }
}
