package com.omnilab.template_kotlin.controller

import com.omnilab.template_kotlin.common.CommonUtils
import com.omnilab.template_kotlin.service.TEMPLATEService
import org.apache.commons.codec.binary.Base64
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.imageio.ImageIO

@Controller
class TestController {

	val logger = LoggerFactory.getLogger(this::class.java)
	
    @Autowired
    @Qualifier("TEMPLATEServiceImpl")
    private lateinit var service: TEMPLATEService

    @RequestMapping(value = arrayOf("/index"), method = arrayOf(RequestMethod.GET))
    fun index(): ModelAndView {
		val view = ModelAndView("index")
        return view
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

}