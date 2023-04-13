package com.omnilab.templateKotlin.config

import com.omnilab.templateKotlin.TemplateKotlinApplication
import com.omnilab.templateKotlin.config.listener.SessionListener
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
		servletContext.addListener(RequestContextListener())
		servletContext.addListener(HttpSessionEventPublisher())
		servletContext.addListener(SessionListener())
		super.onStartup(servletContext)
	}
}

