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
import kotlin.collections.ArrayList

@Configuration
@EnableWebMvc
class MvcConfig : WebMvcConfigurer {

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
        //configurer.enable()
        //super.configureDefaultServletHandling(configurer)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/media/**").addResourceLocations("classpath:/static/media/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/inc/**").addResourceLocations("classpath:/static/inc/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/favicon.ico").setCachePeriod(TimeUnit.DAYS.toSeconds(7).toInt()).resourceChain(true).addResolver(EncodedResourceResolver()).addResolver(PathResourceResolver())
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("forward:/index")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(InterceptorHandler())
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(JacksonConverter())
        converters.add(StringConverter())
        converters.add(byteArrayHttpMessageConverter())
    }

    @Bean // naver-lucy-filter
    fun xssEscapeServletFilter(): FilterRegistrationBean<XssEscapeServletFilter> {
        val filterRegistrationBean: FilterRegistrationBean<XssEscapeServletFilter> = FilterRegistrationBean()
        filterRegistrationBean.filter = XssEscapeServletFilter()
        filterRegistrationBean.order = 1
        return filterRegistrationBean
    }

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

    @Bean //BeanNameViewResolver
    fun beanNameViewResolver(): BeanNameViewResolver {
        val viewResolver = BeanNameViewResolver()
        viewResolver.order = 2
        return viewResolver
    }

    @Bean //뷰 리졸버
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

    @Bean
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

    @Bean(name = ["jsonview"])
    fun jsonview(): MappingJackson2JsonView {
        val viewResolver = MappingJackson2JsonView()
        viewResolver.setPrettyPrint(true)
        return viewResolver
    }

}