package com.mablic.template_kotlin.config.security

import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import java.util.ArrayList

@Service
class CustomUserDetailService : UserDetailsService {

    @Autowired
    private val sqlSession: SqlSession? = null

    override fun loadUserByUsername(username: String): UserDetails {
        /*
        Member_mstDto mstDto =  sqlSession.selectOne("sel_member_mst_id", username);
        if(mstDto == null){
            throw new UsernameNotFoundException("사용자를 찾을수가 없습니다.");
        }else {
            List<GrantedAuthority> roles = new ArrayList<>();
            if(mstDto.getShop_yn() == 0){
                roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            }else{
                roles.add(new SimpleGrantedAuthority("ROLE_SHOP"));
            }
            return new User(mstDto.getMem_id(), "", true, true, true, true, roles);
        }
        */
        return User(username, "", true, true, true, true, null!!)
    }
}
