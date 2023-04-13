package com.omnilab.templatekotlin.service

import com.omnilab.templatekotlin.common.CommonUtils
import com.omnilab.templatekotlin.model.LoginLogDto
import com.omnilab.templatekotlin.model.UserDetailDto
import org.apache.ibatis.session.SqlSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
class UserService : UserDetailsService{

    private val log = LoggerFactory.getLogger(UserService::class.java)

    @Autowired
    private lateinit var sqlSession: SqlSession

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun loadUserByUsername(username: String): UserDetails {
        log.error("loadUserByUsername~!~!~")
        var ip = CommonUtils.clientIp(request)
        if (ip == "0:0:0:0:0:0:0:1")
            ip = "127.0.0.1"

        val dto = sqlSession.selectOne<UserDetailDto>("selByUserName", username)
        if(dto == null){
            throw BadCredentialsException("아이디 또는 패스워드가 틀립니다")
        }else{
            dto.ip = ip
            val logDto = LoginLogDto()
            logDto.emp_id = username
            logDto.ip = ip
            logDto.login_result = 0
            logDto.level = dto.level
            logDto.dept_id = dto.dept_id
            logDto.position = dto.position
            logDto.tm_manager = dto.tm_manager
            sqlSession.insert("loginlog_insert", logDto)

            val roles: MutableList<GrantedAuthority> = ArrayList()
            when (dto.role) {
                "ROLE_USER" -> roles.add(SimpleGrantedAuthority("ROLE_USER"))
                "ROLE_ADMIN" -> roles.add(SimpleGrantedAuthority("ROLE_ADMIN"))
                "ROLE_MANAGER" -> roles.add(SimpleGrantedAuthority("ROLE_MANAGER"))
                "ROLE_NETSALES" -> roles.add(SimpleGrantedAuthority("ROLE_NETSALES"))
                else -> {}
            }
            roles.add(SimpleGrantedAuthority(dto.role))
            dto.setAuthorities(roles)
            return dto
        }
    }

}