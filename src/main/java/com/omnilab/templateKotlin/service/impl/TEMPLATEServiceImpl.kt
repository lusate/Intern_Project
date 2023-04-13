package com.omnilab.templateKotlin.service.impl

import com.omnilab.templateKotlin.service.TEMPLATEService
import org.springframework.stereotype.Service
import com.omnilab.templateKotlin.repository.TEMPLATEDao
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Autowired



@Service("TEMPLATEServiceImpl")
class TEMPLATEServiceImpl: TEMPLATEService {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    @Qualifier("TEMPLATEDaoImpl")
    private lateinit var dao: TEMPLATEDao

    override fun test(): String {
        return dao.test()
    }

    override fun taskService(r: Runnable) {

    }
}