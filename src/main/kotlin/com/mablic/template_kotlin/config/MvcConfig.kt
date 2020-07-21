package com.mablic.template_kotlin.config

import com.mablic.template_kotlin.config.handler.InterceptorHandler
import com.mablic.template_kotlin.config.view.DownloadView
import com.mablic.template_kotlin.config.view.ExcelDownloadView
import com.mablic.template_kotlin.config.view.PrintView
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.mobile.device.DeviceResolverRequestFilter
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.servlet.view.BeanNameViewResolver
import org.springframework.web.servlet.view.InternalResourceViewResolver
import org.springframework.web.servlet.view.json.MappingJackson2JsonView
import org.springframework.web.servlet.view.tiles3.TilesConfigurer
import org.springframework.web.servlet.view.tiles3.TilesView
import org.springframework.web.servlet.view.tiles3.TilesViewResolver
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


@Configuration
@EnableWebMvc
class MvcConfig : WebMvcConfigurer {

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
        //configurer.enable()
        //super.configureDefaultServletHandling(configurer)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/")
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/")
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/")
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/")
        registry.addResourceHandler("/media/**").addResourceLocations("classpath:/static/media/")
        registry.addResourceHandler("/inc/**").addResourceLocations("classpath:/static/inc/")
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico")
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/")
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
    }


    @Bean //필터 등록
    fun deviceFilter(): FilterRegistrationBean<DeviceResolverRequestFilter> {
        val filterRegistrationBean = FilterRegistrationBean<DeviceResolverRequestFilter>()
        filterRegistrationBean.filter = DeviceResolverRequestFilter()
        filterRegistrationBean.order = 2
        return filterRegistrationBean
    }

    @Bean //DispatcherServlet
    fun dispatcherServlet(): DispatcherServlet {
        val ds = DispatcherServlet()
        ds.setThrowExceptionIfNoHandlerFound(true)
        return ds
    }

    @Bean //CharacterEncodingFilter
    fun characterEncodingFilter(): CharacterEncodingFilter {
        val characterEncodingFilter = CharacterEncodingFilter()
        characterEncodingFilter.encoding = "UTF-8"
        characterEncodingFilter.setForceEncoding(true)
        return characterEncodingFilter
    }


    @Bean
    fun tilesConfigurer(): TilesConfigurer? {
        val configurer = TilesConfigurer()
        configurer.setDefinitions("classpath:tiles.xml")
        configurer.setCheckRefresh(true)
        return configurer
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

    //뷰 리졸버
    @Bean
    fun getInternalResourceViewResolver(): InternalResourceViewResolver {
        val viewResolver = InternalResourceViewResolver()
        viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView::class.java)
        viewResolver.setPrefix("/WEB-INF/views/")
        viewResolver.setSuffix(".jsp")
        viewResolver.order = 3
        return viewResolver
    }

    @Bean
    fun jsonview(): MappingJackson2JsonView {
        val viewResolver = MappingJackson2JsonView()
        viewResolver.setPrettyPrint(true)
        return viewResolver
    }


    @Bean
    fun interceptorHandler(): InterceptorHandler {
        return InterceptorHandler()
    }

    //json
    @Bean
    fun JacksonConverter(): MappingJackson2HttpMessageConverter {
        val converter = MappingJackson2HttpMessageConverter()
        val mediatype = ArrayList<MediaType>()
        mediatype.add(MediaType.APPLICATION_JSON)
        converter.supportedMediaTypes = mediatype
        return converter
    }

    //String
    @Bean
    fun StringConverter(): StringHttpMessageConverter {
        val converter = StringHttpMessageConverter(Charset.forName("UTF-8"))
        val mediatype = ArrayList<MediaType>()
        mediatype.add(MediaType.TEXT_HTML)
        mediatype.add(MediaType.APPLICATION_XML)
        converter.supportedMediaTypes = mediatype
        return converter
    }

    //Multipartresolver
    @Bean(name = arrayOf("multipartResolver"))
    @Throws(IOException::class)
    fun multipartResolver(): CommonsMultipartResolver {
        val multipartresolver = CommonsMultipartResolver()
        val uploadDirResource = FileSystemResource(System.getProperty("user.dir") + "/src/main/webapp/temp")
        multipartresolver.setMaxUploadSize(52428800)
        multipartresolver.setDefaultEncoding("UTF-8")
        multipartresolver.setUploadTempDir(uploadDirResource)
        return multipartresolver
    }

    @Bean(name = arrayOf("downloadView"))
    fun downloadview(): DownloadView {
        return DownloadView()
    }

    @Bean(name = arrayOf("ExcelDownView"))
    fun excelDownLoadView(): ExcelDownloadView {
        return ExcelDownloadView()
    }

    @Bean(name = arrayOf("PrintView"))
    fun printview(): PrintView {
        return PrintView()
    }
}