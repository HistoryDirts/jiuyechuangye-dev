package com.example.demo.common.config;

import com.example.demo.interceptor.MyInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Resource
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**")     //配置拦截路径
                .excludePathPatterns("/user/login", "/user/register", "/user/saveUserInfo");    //配置排除路径

        // registry.addInterceptor(authInterceptor())
        //         .addPathPatterns("/**")
        //         .excludePathPatterns("/user/login", "/user/register", "/imserver/**", "/files/**", "/alipay/**",
        //                 "/doc.html", "/webjars/**", "/swagger-resources/**");

    }

    private HandlerInterceptor authInterceptor() {
        return null;
    }

    
}
