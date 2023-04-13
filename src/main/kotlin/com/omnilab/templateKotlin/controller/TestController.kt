package com.omnilab.templateKotlin.controller

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class TestController {
    val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/test")
    fun test(@RequestParam n: Int): String {
        return if (n >= 0) {
            "hello"
        } else {
            "world"
        }
    }

}
