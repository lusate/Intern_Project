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
class TestController {

	val logger = LoggerFactory.getLogger(this::class.java)
	
    @Autowired
    @Qualifier("TEMPLATEServiceImpl")
    private lateinit var service: TEMPLATEService

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @RequestMapping(value = arrayOf("/index", "/index.mi"), method = arrayOf(RequestMethod.GET))
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

    @GetMapping("/cimg")
    fun getImage(@RequestParam("p") path: String): ModelAndView {
        val view = ModelAndView("img")
        var imgStr = ""
        val url = CommonUtils.encodeURI(path)
        try{
            val u = URL(url)
            val img: BufferedImage = ImageIO.read(u)
            val bos = ByteArrayOutputStream()
            ImageIO.write(img, "PNG", bos)
            imgStr = String(Base64.encodeBase64(bos.toByteArray()), StandardCharsets.UTF_8)
        }catch (e: Exception){
            logger.error("IMG ERROR", e)
        }
        view.addObject("img", imgStr)
        return view
    }

    @GetMapping("/cimgd")
    fun getImaged(@RequestParam("p") path: String): ModelAndView {
        val view = ModelAndView("imgd")
        view.addObject("img", CommonUtils.encodeURI(path))
        return view
    }


    @GetMapping("/test/t_iframe")
    fun t_iframe(req: HttpServletRequest, rep: HttpServletResponse, session: HttpSession): ModelAndView {
        val dataMap = LinkedMultiValueMap<String, Any>()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        dataMap.add("username", "amy")
        val result: ResponseEntity<String> = restTemplate.exchange("https://tableau-report.com/trusted", HttpMethod.POST, HttpEntity(dataMap, headers), String::class.java)
        val view = ModelAndView("tableau/iframe")
        view.addObject("ticket", result.body)
        return view
    }

    @GetMapping("/test/t_object")
    fun t_object(req: HttpServletRequest, rep: HttpServletResponse, session: HttpSession): ModelAndView {
        val dataMap = LinkedMultiValueMap<String, Any>()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        dataMap.add("username", "Henry")
        val result: ResponseEntity<String> = restTemplate.exchange("https://tableau-report.com/trusted", HttpMethod.POST, HttpEntity(dataMap, headers), String::class.java)
        val view = ModelAndView("tableau/object")
        view.addObject("ticket", result.body)
        return view
    }

    @GetMapping("/test/t_script")
    fun t_script(req: HttpServletRequest, rep: HttpServletResponse, session: HttpSession): ModelAndView {
        val dataMap = LinkedMultiValueMap<String, Any>()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        dataMap.add("username", "Martin")
        val result: ResponseEntity<String> = restTemplate.exchange("https://tableau-report.com/trusted", HttpMethod.POST, HttpEntity(dataMap, headers), String::class.java)
        val view = ModelAndView("tableau/script")
        view.addObject("ticket", result.body)
        return view
    }
}