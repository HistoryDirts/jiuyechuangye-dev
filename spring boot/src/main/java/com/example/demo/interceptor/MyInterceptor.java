package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.entity.User;
import com.example.demo.utils.JwtUtil;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器...");

        //获取请求头中的token
        String token = request.getHeader("token");

        //判断token是否为空
        if(!StringUtils.hasText(token)) {
            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            throw new RuntimeException("未登录，请登录后重试");
            // return false;
        }
        
        //解析token
        try {
            // String aud = JWT.decode(token).getAudience().get(0);
            // Integer userId = Integer.valueOf(aud);
            User user = JwtUtil.parseToken(token);
            System.out.println("userId:" + user.getId());
        } catch (Exception e) {
            //如果解析出现异常，说明未登录(401)，否则是登录状态
            e.printStackTrace();
            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            // return false;
            throw new RuntimeException("token解析失败，请登录后重试");
        }

        //如果解析正常，则放行
        return true;
      }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}