package com.omnilab.templatekotlin.config

import com.omnilab.templatekotlin.config.handler.AuthenticationHandler
import com.omnilab.templatekotlin.config.handler.LoginFailHandler
import com.omnilab.templatekotlin.config.handler.LoginSuccessHandler
import com.omnilab.templatekotlin.service.CustomAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity.RequestMatcherConfigurer
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.firewall.StrictHttpFirewall
import org.springframework.web.filter.CharacterEncodingFilter
import java.util.*
import javax.servlet.DispatcherType
import javax.servlet.Filter

@Configuration
class SecurityConfig {

    private val INDEXPAGE = "index.mi"

    @Autowired
    private lateinit var authenticationhandler: AuthenticationHandler

    @Autowired
    private lateinit var customAuthenticationProvider: CustomAuthenticationProvider

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            val firewall = StrictHttpFirewall()
            // firewall.setAllowUrlEncodedPercent(true);
            firewall.setAllowUrlEncodedSlash(true)
            firewall.setAllowUrlEncodedDoubleSlash(true)

            web.httpFirewall(firewall)
        }
    }

    // 리소스
    @Bean
    @Order(0)
    fun resources(http: HttpSecurity): SecurityFilterChain {
        return http.requestMatchers { matchers: RequestMatcherConfigurer ->
            matchers.antMatchers("/font/**", "/css/**", "/js/**", "/img/**", "/inc/**", "/js/**", "/favicon.ico", "/html/**", "/*.ico")
        }
            .authorizeHttpRequests { authorize: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
                authorize.anyRequest().permitAll()
            }
            .requestCache { obj: RequestCacheConfigurer<HttpSecurity> -> obj.disable() }
            .securityContext { obj: SecurityContextConfigurer<HttpSecurity> -> obj.disable() }
            .sessionManagement { obj: SessionManagementConfigurer<HttpSecurity> -> obj.disable() }
            .build()
    }

    // 일반
    @Bean
    @Order(1)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        val filter = CharacterEncodingFilter()
        filter.encoding = "UTF-8"
        filter.setForceEncoding(true)

        return httpSecurity.addFilterBefore(filter, CsrfFilter::class.java)
            .csrf { csrf: CsrfConfigurer<HttpSecurity> ->
                csrf.ignoringAntMatchers("/rest/**", "/api/**")
            }
            .authorizeRequests { authorize: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry ->
                authorize
                    .antMatchers(INDEXPAGE, "/", "/index", "/loginProcess.service", "/logout.service", "/error.mi", "/cimg/**", "/cimgd/**", "/test/**").permitAll()
                    // .antMatchers("/user").hasRole("USER")
                    .anyRequest().authenticated()
            }
            // 로그인 설정
            .formLogin { form: FormLoginConfigurer<HttpSecurity> ->
                form
                    .loginPage(INDEXPAGE)
                    .loginProcessingUrl("/loginProcess.service")
                    .usernameParameter("id")
                    .passwordParameter("password")
                    .successHandler(LoginSuccessHandler())
                    .failureHandler(LoginFailHandler())
            }
            // 로그아웃 설정
            .logout { logout: LogoutConfigurer<HttpSecurity> ->
                logout
                    .logoutUrl("/logout.service")
                    .logoutSuccessUrl(INDEXPAGE)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "TEMPLATE")
            }
            // 해들링 설정
            .exceptionHandling { exceptionHandling: ExceptionHandlingConfigurer<HttpSecurity> ->
                exceptionHandling
                    .authenticationEntryPoint(authenticationhandler)
                    .accessDeniedHandler(authenticationhandler)
            }
            // remember me 설정
            .rememberMe { rememberMe: RememberMeConfigurer<HttpSecurity> ->
                rememberMe
                    .key("template")
                    .rememberMeParameter("remember_me")
                    .rememberMeCookieName("TEMPLATE")
                    .tokenValiditySeconds(864000)
            }
            // session
            .sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity> ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            }
            // 헤더 설정
            .headers { headers: HeadersConfigurer<HttpSecurity> ->
                headers
                    .xssProtection { }
                    .contentSecurityPolicy("frame-ancestors 'self' *.milvusapp.com tableau-report.com *.tableau-report.com;")
            }
            .build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun getSpringSecurityFilterChainBindedToError(@Qualifier("springSecurityFilterChain") springSecurityFilterChain: Filter): FilterRegistrationBean<*> {
        val registration = FilterRegistrationBean<Filter>()
        registration.filter = springSecurityFilterChain
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType::class.java))
        return registration
    }
}
