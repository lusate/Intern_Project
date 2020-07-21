package com.mablic.template_kotlin.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Autowired
import com.mablic.template_kotlin.service.TEMPLATEService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import com.mablic.template_kotlin.Tests
import org.springframework.web.servlet.ModelAndView

@Controller
class TestController {

	val logger = LoggerFactory.getLogger(this::class.java)
	
    @Autowired
    @Qualifier("TEMPLATEServiceImpl")
    private val service: TEMPLATEService? = null

    @RequestMapping(value = arrayOf("/index"), method = arrayOf(RequestMethod.GET))
    fun index(): ModelAndView {
		//logger.error(service!!.test());
        val view = ModelAndView("index")
        view.addObject("index", true)
        view.addObject("index2", true)
        view.addObject("index_view", "body")
        view.addObject("index2_view", "body2")
        view.addObject("test", "테스트 밸류")
        view.addObject("test2", "테스트2 벨류")
        return view
    }
	
	@GetMapping("/test")
    fun test(): String {
		//100 / 0
		val t = Tests()
		t.tttt()
        return "index"
    }
}