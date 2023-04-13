package com.omnilab.templatekotlin

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class TemplateKotlinApplicationTests {

	@Autowired
	private lateinit var mvc: MockMvc

	@Test
	fun test() {
		mvc.perform(get("/test").param("n", "3"))
			.andExpect(content().string("hello"))
			.andExpect(status().isOk)
	}

}

