package com.hxzy.conf;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hxzy.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * SpringMVC的前端控制器配置
 */
@Configuration
public class ZiYunMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 自定义消息类型转换
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //添加fastjson
        FastJsonHttpMessageConverter fastjson=new FastJsonHttpMessageConverter();
        //自定义supportedMediaTypes
        List<MediaType> supportedMediaTypes =new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastjson.setSupportedMediaTypes(supportedMediaTypes);
        //把fastjson放到 converters
        converters.add(fastjson);

        //解决下载文件二进制编码出错 org.springframework.http.converter.ByteArrayHttpMessageConverter
        ByteArrayHttpMessageConverter  byteArray=new ByteArrayHttpMessageConverter();
        converters.add(byteArray);

        super.configureMessageConverters(converters);
    }


    //后台拦截器
    @Autowired
    private AdminInterceptor  adminInterceptor;

    /**
     * 自定义拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
         //后台拦截器
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login");

        super.addInterceptors(registry);
    }

    /**
     * 静态资源放行，(图片服务器)
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //当前项目的静态资源放行
        /* springmvc以前的静态资源放行配置
         <mvc:resources mapping="/static/**" location="/static/"></mvc:resources>
         */
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");


        //图片服务器
        registry.addResourceHandler("/imgcms/**").addResourceLocations("file:///D:/imgServer/");

        super.addResourceHandlers(registry);
    }

    /**
     * 跨域请求放行配置
     * @param registry
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        //把 /admin/** 请求都放行
        registry.addMapping("/admin/**")
                .allowCredentials(true)  //是否允许推荐cookie
                .allowedOrigins("*")        //哪些网站能访问
                .allowedMethods("*")
                .maxAge(1800);    //访问缓存时间 1800秒 o 1800 seconds (30 minutes)

        //还要添加很多

        super.addCorsMappings(registry);
    }
}
