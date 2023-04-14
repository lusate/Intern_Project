package com.omnilab.templatekotlin.service.impl

import com.omnilab.templatekotlin.repository.TEMPLATEDao
import com.omnilab.templatekotlin.service.TemplateService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service("TEMPLATEServiceImpl")
class TemplateServiceImpl : TemplateService {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    @Qualifier("TEMPLATEDaoImpl")
    private lateinit var dao: TEMPLATEDao

    override fun test(): String {
        return dao.test()
    }

    override fun taskService(r: Runnable) {
        TODO("Not yet implemented")
    }

}
