package com.omnilab.template_kotlin.config

import com.omnilab.template_kotlin.config.handler.AuthenticationHandler
import com.omnilab.template_kotlin.config.handler.LoginFailHandler
import com.omnilab.template_kotlin.config.handler.LoginSuccessHandler
import com.omnilab.template_kotlin.service.CustomAuthenticationProvider
import org.slf4j.LoggerFactory

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.firewall.StrictHttpFirewall
import org.springframework.security.web.firewall.HttpFirewall
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import java.util.EnumSet

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.header.HeaderWriter
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter

import javax.servlet.DispatcherType
import javax.servlet.Filter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var authenticationhandler: AuthenticationHandler

    @Autowired
    private lateinit var customAuthenticationProvider: CustomAuthenticationProvider

    override fun configure(web: WebSecurity) {
        super.configure(web)
        web.ignoring().antMatchers("/font/**", "/css/**", "/js/**", "/img/**", "/inc/**", "/js/**", "/favicon.ico", "/html/**", "/*.ico")
        web.httpFirewall(allowUrlHttpFirewall())
    }

    override fun configure(httpSecurity: HttpSecurity) {
        val filter = CharacterEncodingFilter()
        filter.encoding = "UTF-8"
        filter.setForceEncoding(true)
        httpSecurity.addFilterBefore(filter, CsrfFilter::class.java)
                .csrf().ignoringAntMatchers("/rest/**", "/api/**")
            .and()
                .authorizeRequests().antMatchers("/", "/index", "/login", "/loginProcess.service", "/logout.service", "/error", "/cimg/**", "/cimgd/**", "/test/**").permitAll()
            .and()
                .authorizeRequests().anyRequest().authenticated()
            // 로그인 설정
            .and()
                .formLogin()
                .loginPage("/index")
                .loginProcessingUrl("/login.service")
                .usernameParameter("id")
                .passwordParameter("password")
                .successHandler(LoginSuccessHandler())
                .failureHandler(LoginFailHandler())
            // 로그아웃 설정
            .and()
                .logout()
                .logoutUrl("/logout.service")
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "TEMPLATE")
            // 해들링 설정
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationhandler)
                .accessDeniedHandler(authenticationhandler)
            // remember me 설정
            .and()
                .rememberMe()
                .key("template")
                .rememberMeParameter("remember_me")
                .rememberMeCookieName("TEMPLATE")
                .tokenValiditySeconds(864000)
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
                //.headers().frameOptions().sameOrigin()
                .headers()
                    .xssProtection()
                .and()
                    .contentSecurityPolicy("frame-ancestors 'self' http://lucas.milvusapp.com:8081;")
                //.headers().frameOptions().disable().addHeaderWriter(CustomXFrameOptionsHeaderWriter())
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(customAuthenticationProvider)
    }

    @Bean
    fun getSpringSecurityFilterChainBindedToError(@Qualifier("springSecurityFilterChain") springSecurityFilterChain: Filter): FilterRegistrationBean<*> {
        val registration = FilterRegistrationBean<Filter>()
        registration.filter = springSecurityFilterChain
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType::class.java))
        return registration
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun allowUrlHttpFirewall(): HttpFirewall {
        val firewall = StrictHttpFirewall()
        //firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedSlash(true)
        firewall.setAllowUrlEncodedDoubleSlash(true)
        return firewall
    }

    private class CustomXFrameOptionsHeaderWriter : HeaderWriter {
        private val defaultHeaderWriter: XFrameOptionsHeaderWriter = XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)
        private val allowUrl: String = "\\/sf\\/.*"
        private val log = LoggerFactory.getLogger(this.javaClass)

        override fun writeHeaders(req: HttpServletRequest, rep: HttpServletResponse) {
            log.error("Referer : {}", req.getHeader("Referer"))
            log.error("HOST : {}", req.getHeader("HOST"))
            log.error("X-FORWARDED-HOST : {}", req.getHeader("X-FORWARDED-HOST"))

            if (!req.requestURI.contains(allowUrl)) {
                defaultHeaderWriter.writeHeaders(req, rep)
            }
        }
    }

}