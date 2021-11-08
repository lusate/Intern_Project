package com.omnilab.template_kotlin.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.omnilab.template_kotlin.common.CommonUtils
import com.omnilab.template_kotlin.service.TEMPLATEService
import org.apache.commons.codec.binary.Base64
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.*
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.ModelAndView
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class MainController {

	val logger = LoggerFactory.getLogger(this::class.java)
	
    @Autowired
    @Qualifier("TEMPLATEServiceImpl")
    private lateinit var service: TEMPLATEService

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @RequestMapping(value = ["/index", "/index.mi"], method = [RequestMethod.GET])
    fun index(): ModelAndView {
		val view = ModelAndView("index")
        return view
    }

    @RequestMapping("/error.mi")
    fun error(req: HttpServletRequest, rep: HttpServletResponse, session: HttpSession): String {
        try{
            val throwable = req.getAttribute("javax.servlet.error.exception") as Throwable
            val statusCode = req.getAttribute("javax.servlet.error.status_code") as Int
            var servletName = req.getAttribute("javax.servlet.error.servlet_name") as String?
            if (servletName == null) {
                servletName = "Unknown"
            }
            var requestUri = req.getAttribute("javax.servlet.error.request_uri") as String?
            if (requestUri == null) {
                requestUri = "Unknown"
            }
            logger.error("##### Error information #####")
            logger.error("The status code : {}", statusCode)
            logger.error("Servlet Name : {}", servletName)
            logger.error("The request URI: {}", requestUri)
            logger.error("ERROR : ", throwable)
            logger.error("#########################")
        }catch (e: Exception) {
            logger.error("EROOR Controller", e)
        }
        return "redirect:/index.mi"
    }

}