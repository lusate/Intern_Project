package com.mablic.template_kotlin.config

import com.mablic.template_kotlin.config.listener.SessionListener
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import javax.servlet.ServletContext
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.web.context.request.RequestContextListener



class ServletInitializer : SpringBootServletInitializer() {

	init {
	    setRegisterErrorPageFilter(false)
	}

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(TemplateKotlinApplication::class.java)
	}

	override fun onStartup(servletContext: ServletContext) {
		super.onStartup(servletContext)
		servletContext.addListener(RequestContextListener())
		servletContext.addListener(HttpSessionEventPublisher())
		servletContext.addListener(SessionListener())
	}
}

