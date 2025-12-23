package com.example.demo2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 配置默认首页为视频列表页面和权限拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 将根路径重定向到视频列表页面
        registry.addViewController("/").setViewName("redirect:/video-list.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 管理员权限拦截器 - 拦截所有 /admin/** API路径（除了登录接口）
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns(
                    "/api/admin/login",
                    "/admin-login.html",
                    "/admin-dashboard.html",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js",
                    "/**/*.png",
                    "/**/*.jpg",
                    "/**/*.gif"
                );

        // 用户权限拦截器 - 拦截需要登录的用户接口
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/api/user/profile/**", "/api/user/current")
                .excludePathPatterns(
                    "/api/user/login",
                    "/api/user/register",
                    "/api/user/check",
                    "/user-login.html",
                    "/register.html",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js"
                );
    }
}

