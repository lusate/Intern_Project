package com.omnilab.templatekotlin.config

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter
import com.omnilab.templatekotlin.config.handler.InterceptorHandler
import com.omnilab.templatekotlin.config.view.DownloadView
import com.omnilab.templatekotlin.config.view.ExcelDownloadView
import com.omnilab.templatekotlin.config.view.PrintView
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.mobile.device.DeviceResolverRequestFilter
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import org.springframework.web.servlet.resource.EncodedResourceResolver
import org.springframework.web.servlet.resource.PathResourceResolver
import org.springframework.web.servlet.view.BeanNameViewResolver
import org.springframework.web.servlet.view.InternalResourceViewResolver
import org.springframework.web.servlet.view.json.MappingJackson2JsonView
import org.springframework.web.servlet.view.tiles3.TilesConfigurer
import org.springframework.web.servlet.view.tiles3.TilesView
import org.springframework.web.servlet.view.tiles3.TilesViewResolver
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit


@Configuration
@EnableWebMvc
class MvcConfig : WebMvcConfigurer {

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
        //configurer.enable()
        //super.configureDefaultServletHandling(configurer)
    }

    //ResourceHandler : 이미지, javascript, css, html 등의 정적인 리소스에 대한 요청을 처리하는 것
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/media/**").addResourceLocations("classpath:/static/media/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/inc/**").addResourceLocations("classpath:/static/inc/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/favicon.ico").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())

        //template의 thymeleaf
        // addResourceHandler() : Resource를 요청하기 위한 기본 URL-Pattern을 입력 / 아래로 모든 Resource 요청을 받는다.
        // addResourceLocations("classpath:/templates/") : addResourceHandler에서 설정한 / URL로 들어오는 모든 Resource 요청을 /templates 하위 디렉토리에서 찾으라는 의미.
        // setCachPeriod(0) : Resource의 캐시 기간을 설정하기 위함. 0으로 설정하면 서버에 주기적으로 요청하지 않고 Resource가 변경이 있을 경우에만 캐시가 재설정됨.
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())

    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("forward:/index") //시작 페이지 -> / url이 요청되면 index라는 view로 이동하게 해준다.
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(InterceptorHandler())  // 인터셉터 등록
    }


    // HttpMessageConverter : 사용자의 요청 본문에 담긴 내용을 Controller 에서 사용하기위한 Argument로 변환하거나, 서버에서 생성한 응답 결과를 응답 본문에 작성할 때 사용
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(JacksonConverter())  // 외부에서 전달받은 json을 내부에서 사용할 수 있는 객체로 변환하거나 클라이언트에게 json으로 변환해서 전달할 수 있는 역할
        converters.add(StringConverter())
        converters.add(byteArrayHttpMessageConverter())
    }

    @Bean // naver-lucy-filter -> 웹애플리케이션으로 들어오는 모든 요청 파라미터에 대해 기본적으로 XSS 방어 필터링을 수행
    fun xssEscapeServletFilter(): FilterRegistrationBean<XssEscapeServletFilter> {
        val filterRegistrationBean: FilterRegistrationBean<XssEscapeServletFilter> = FilterRegistrationBean()
        filterRegistrationBean.filter = XssEscapeServletFilter()
        filterRegistrationBean.order = 1
        return filterRegistrationBean
    }
    // xss는 웹 애플리케이션에 악의적으로 스크립트를 삽입해 공격하는 기법.
    // 데이터를 서버로 저장할 때(게시판에 글을 쓴다던지. 회원 정보를 수정한다던지) 데이터를 검증하지 않거나 XSS에 대한 방어 대비가 없다면
    // 스크립트가 포함된 데이터가 저장되어 유저로 하여금 원치 않는 스크립트를 실행시킬 수 있다.



    @Bean //필터 등록
    fun deviceFilter(): FilterRegistrationBean<DeviceResolverRequestFilter> {
        val filterRegistrationBean = FilterRegistrationBean<DeviceResolverRequestFilter>()
        filterRegistrationBean.filter = DeviceResolverRequestFilter()
        filterRegistrationBean.order = 2
        return filterRegistrationBean
    }



    /*
    @Bean //사이트 메쉬 필러 등록
    fun siteMeshFilter(): FilterRegistrationBean<SiteMeshFilter>? {
        val filterRegistrationBean: FilterRegistrationBean<SiteMeshFilter> = FilterRegistrationBean()
        filterRegistrationBean.filter = SiteMeshFilter()
        filterRegistrationBean.order = 3
        return filterRegistrationBean
    }
    */

    @Bean //DispatcherServlet
    fun dispatcherServlet(): DispatcherServlet {
        val ds = DispatcherServlet()
        ds.setThrowExceptionIfNoHandlerFound(true)
        return ds
    }

    @Bean
    fun tilesViewResolver(): TilesViewResolver? {
        val viewResolver = TilesViewResolver()
        viewResolver.setViewClass(TilesView::class.java)
        viewResolver.order = 1
        return viewResolver
    }
    // Tiles는 (Composite View 패턴) 웹 페이지의 상단이나 하단 메뉴와 같이 반복적으로 사용되는 부분들에 대한 코드를 분리해서 예쁘게 한 곳에서 관리를 가능하게 해주는 프레임워크
    // SiteMesh는 Decorator 패턴 사용.
    // 페이지의 모든 부분을 모두 재사용 가능.
    // SiteMesh는 각각의 데코레이터를 재사용 할수는 있지만, 데코레이션 자체는 한 페이지씩 적용할 수 있다.

    // JSP include와의 차이 : jsp는 페이지 내에 동일한 레이아웃 정보가 들어가므로 전체적인 레이아웃을 변경하게 될 경우 모든 페이지를 수정해야하는 문제점이 있는데 tiles는 이런 일이 있으면 설정 파일만 변경해주면 된다.



    @Bean //BeanNameViewResolver : 뷰 이름과 동일한 이름을 가지는 빈을 view 객체로 사용 (빈 이름으로 뷰를 찾음)
    fun beanNameViewResolver(): BeanNameViewResolver {
        val viewResolver = BeanNameViewResolver()
        viewResolver.order = 2
        return viewResolver
    }// 모든 View 페이지가 동일한 경로에 저장된 경우 ViewResolver의 기본 클래스만 사용해도 되지만 서로 다른 폴더에 저장된 경우 view 페이지마다 다른 경로를 지정해야 한다.



    @Bean //뷰 리졸버 : ModelAndView 객체를 View 영역으로 전달하기 위해 알맞은 View 정보를 설정하는 역할을 한다. (사용자에게 결과를 랜더링하여 보여주기 위해 사용.)
    fun getInternalResourceViewResolver(): InternalResourceViewResolver {
        val viewResolver = InternalResourceViewResolver()
        viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView::class.java)
        viewResolver.setPrefix("/WEB-INF/views/")
        viewResolver.setSuffix(".jsp")
        viewResolver.order = 3
        return viewResolver
    }


    @Bean // 로케일 리졸버
    fun localResolver(): LocaleResolver {
        val re = SessionLocaleResolver()
        re.setDefaultLocale(Locale.KOREA)
        return re
    }

    @Bean  //CharacterEncodingFilter : 한글 깨짐 해결
    fun localeChangeInterceptor() : LocaleChangeInterceptor {
        val re = LocaleChangeInterceptor()
        re.paramName = "ln"
        return re
    }

    @Bean
    fun messageSource(): ReloadableResourceBundleMessageSource {
        val re = ReloadableResourceBundleMessageSource()
        re.setBasename("classpath:/locale/messages")
        re.setDefaultEncoding(StandardCharsets.UTF_8.toString())
        re.setUseCodeAsDefaultMessage(true)
        re.setCacheSeconds(60)
        return re
    }

    @Bean //json
    fun JacksonConverter(): MappingJackson2HttpMessageConverter {
        val converter = MappingJackson2HttpMessageConverter()
        val mediatype = ArrayList<MediaType>()
        mediatype.add(MediaType.APPLICATION_JSON)
        converter.supportedMediaTypes = mediatype
        return converter
    }



    @Bean //CharacterEncodingFilter
    fun characterEncodingFilter(): CharacterEncodingFilter {
        val characterEncodingFilter = CharacterEncodingFilter()
        characterEncodingFilter.encoding = "UTF-8"
        characterEncodingFilter.setForceEncoding(true)
        return characterEncodingFilter
    }

    @Bean //String
    fun StringConverter(): StringHttpMessageConverter {
        val converter = StringHttpMessageConverter(Charset.forName("UTF-8"))
        val mediatype = ArrayList<MediaType>()
        mediatype.add(MediaType.TEXT_HTML)
        mediatype.add(MediaType.APPLICATION_XML)
        converter.supportedMediaTypes = mediatype
        return converter
    }

    @Bean
    fun byteArrayHttpMessageConverter(): ByteArrayHttpMessageConverter {
        val converter = ByteArrayHttpMessageConverter()
        val mediaType = ArrayList<MediaType>()
        mediaType.add(MediaType.IMAGE_JPEG)
        mediaType.add(MediaType.IMAGE_GIF)
        mediaType.add(MediaType.IMAGE_PNG)
        mediaType.add(MediaType.APPLICATION_OCTET_STREAM)
        converter.supportedMediaTypes = mediaType
        return converter
    }

    @Bean
    fun tilesConfigurer(): TilesConfigurer? {
        val configurer = TilesConfigurer()
        configurer.setDefinitions("classpath:tiles.xml")
        configurer.setCheckRefresh(true)
        return configurer
    }

    //Multipartresolver
    @Bean(name = ["multipartResolver"])
    fun multipartResolver(): CommonsMultipartResolver {
        val multipartresolver = CommonsMultipartResolver()
        val uploadDirResource = FileSystemResource(System.getProperty("user.dir") + "/src/main/webapp/temp")
        multipartresolver.setMaxUploadSize(52428800)
        multipartresolver.setDefaultEncoding("UTF-8")
        multipartresolver.setUploadTempDir(uploadDirResource)
        return multipartresolver
    }

    @Bean(name = ["downloadView"])
    fun downloadview(): DownloadView {
        return DownloadView()
    }

    @Bean(name = ["ExcelDownView"])
    fun excelDownLoadView(): ExcelDownloadView {
        return ExcelDownloadView()
    }

    @Bean(name = ["PrintView"])
    fun printview(): PrintView {
        return PrintView()
    }

}


// 필터, xss, 사이트 메쉬/타일즈, 컨버터, 리졸버, 언어 메시지 컨버터, OtherConfig, view, multipart, 스케줄러, task
// rest, settions.xml, 소스 구조 파악.