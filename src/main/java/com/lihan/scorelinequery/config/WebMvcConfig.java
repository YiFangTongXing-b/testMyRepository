package com.lihan.scorelinequery.config;


import com.lihan.scorelinequery.interceptor.AdminLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                // 那些路径不拦截
                .excludePathPatterns("/login");
    }

    @Bean
    public AdminLoginInterceptor loginInterceptor(){
        return new AdminLoginInterceptor();
    }
}