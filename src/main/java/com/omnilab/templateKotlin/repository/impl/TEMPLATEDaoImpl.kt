package com.omnilab.templateKotlin.repository.impl

import com.omnilab.templateKotlin.repository.TEMPLATEDao
import org.apache.ibatis.session.SqlSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component("TEMPLATEDaoImpl")
class TEMPLATEDaoImpl: TEMPLATEDao{

    @Autowired
    private lateinit var sqlSession: SqlSession

    override fun test(): String {
        return sqlSession.selectOne<String>("select2")
    }
}