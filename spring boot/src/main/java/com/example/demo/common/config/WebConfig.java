package com.example.demo.common.config;

import com.example.demo.interceptor.MyInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author Wu Yong
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Resource
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**")     //配置拦截路径
                .excludePathPatterns("/error","/user/login", "/user/register");    //配置排除路径
    }
}
