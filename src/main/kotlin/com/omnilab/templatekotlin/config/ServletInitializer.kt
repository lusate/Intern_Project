package com.omnilab.templatekotlin.config

import com.omnilab.templatekotlin.TemplateKotlinApplication
import com.omnilab.templatekotlin.config.listener.SessionListener
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


// SpringBootServletInitializer : war 파일로 배포를 진행해야 하는 경우 SpringBootServletInitializer를 상속받는다. 즉 war 파일로 배포하지 않는다면 상속 받지 않아도 된다.
// war 배포할 때 '' 상속받는 이유 -> Spring 웹 애플리케이션을 외부 Tomcat에서 동작하도록 하기 위해서는 web.xml에 애플리케이션 컨텍스트를 등록해야만 한다.
// 이는, Apache Tomcat이 구동될 때 /WEB-INF 디렉토리에 존재하는 web.xml을 읽어 웹 애플리케이션을 구성하기 때문이다.