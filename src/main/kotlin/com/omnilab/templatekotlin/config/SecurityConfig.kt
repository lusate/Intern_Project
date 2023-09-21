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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.firewall.StrictHttpFirewall
import org.springframework.web.filter.CharacterEncodingFilter
import java.util.*
import javax.servlet.DispatcherType
import javax.servlet.Filter


@Configuration
class SecurityConfig {

    private val INDEXPAGE = "/index.mi"

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

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
            matchers.antMatchers("/font/**", "/css/**", "/js/**", "/img/**", "/inc/**", "/js/**", "/favicon.ico", "/html/**", "/*.ico", "js/*.js", "/css/*.css")
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
//            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF Token을 지정
//            .and()
            .csrf { csrf: CsrfConfigurer<HttpSecurity> ->
                csrf.ignoringAntMatchers("/rest/**", "/api/**")
            }



            // 접근 권한 설정
            .authorizeRequests { authorize: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry ->
                authorize
//                    .antMatchers(INDEXPAGE, "/", "/index", "/loginProcess.service", "/logout.service", "/error.mi", "/cimg/**", "/cimgd/**", "/test/**").permitAll()
                    .antMatchers(
                        INDEXPAGE,
                        "/",
                        "/index",
                        "/logout",
                        "/error.mi",
                        "/cimg/**",
                        "/cimgd/**",
                        "/test/**",
                        "/members/new",
                        "/duplicate"
                    ).permitAll()  // "members/new" 는 회원 가입.

                    // Spring Security에서 prefix를 자동으로 "ROLE_"을 넣어주므로 이 때 hasRole에는 ROLE을 제외하고 뒷 부분인 ADMIN만 써주면 된다
                    .antMatchers("/members").hasRole("ADMIN")
                    .antMatchers("/items/new").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }


            // 중복 로그인 설정
            .sessionManagement { management: SessionManagementConfigurer<HttpSecurity> ->
                management.maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .sessionRegistry(sessionRegistry())
                //.expiredUrl()
            }
            // 로그인 설정
            .formLogin { form: FormLoginConfigurer<HttpSecurity> ->
                form
                    .loginPage(INDEXPAGE)
//                    .loginProcessingUrl("/loginProcess.service")
                    .loginProcessingUrl("/home")
                    .usernameParameter("id")
                    .passwordParameter("password")
                    .successHandler(LoginSuccessHandler())
                    .failureHandler(LoginFailHandler())
            }
            // 로그아웃 설정
            .logout { logout: LogoutConfigurer<HttpSecurity> ->
                logout
//                    .logoutUrl("/logout.service")
                    .logoutUrl("/logout")
                    .logoutSuccessUrl(INDEXPAGE)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "TEMPLATE")
            }
            // 핸들링 설정
            .exceptionHandling { exceptionHandling: ExceptionHandlingConfigurer<HttpSecurity> ->
                exceptionHandling
                    .authenticationEntryPoint(authenticationhandler)
                    .accessDeniedHandler(authenticationhandler)
            }
            // remember me 설정
            .rememberMe { rememberMe: RememberMeConfigurer<HttpSecurity> ->
                rememberMe
                    .key("template")
                    .rememberMeParameter("remember_me") //아이디 비번 기억
                    .rememberMeCookieName("TEMPLATE") // 쿠키명 변경
                    .tokenValiditySeconds(864000) // 쿠키의 만료시간 설정
//                    .userDetailsService(userDetailsService) //remember-me 토큰이 올바른 권한을 포함할 수 있도록 필요.
                    //remember-me 기능을 수행할 때 시스템에 있는 사용자 계정을 조회하는 과정이 있음 그때 필요한 클래스가 필수적으로 설정해야 함.

                // rememberMeParameter ("remember_me") 라고 설정했다면 html 에서도
                //<input type="checkbox" name="remember_me" /> 라고 해야 정상 동작
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

    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

}
