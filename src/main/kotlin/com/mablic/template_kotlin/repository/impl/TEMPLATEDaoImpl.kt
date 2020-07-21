package com.mablic.template_kotlin.repository.impl

import com.mablic.template_kotlin.repository.TEMPLATEDao
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component("TEMPLATEDaoImpl")
class TEMPLATEDaoImpl: TEMPLATEDao{

    @Autowired
    private val sqlSession: SqlSession? = null

    override fun test(): String {
        return sqlSession!!.selectOne<String>("select2")
    }
}