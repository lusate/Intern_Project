package com.omnilab.templatekotlin.repository.member;

import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.member.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username); // 관리자, 유저를 찾기 위함


    Optional<Member> findByLoginId(String loginId);


    // 중복 확인 쿼리
//    @Query("SELECT COUNT(m.loginId) FROM Member m where m.loginId = :loginId")
//    int findMemberByLoginId(@Param("loginId") String loginId);

    int countByLoginId(@Param("loginId") String loginId);

    Page<Member> findAll(Pageable pageable);

}


// No property 'findPassword' found for type 'Member'
