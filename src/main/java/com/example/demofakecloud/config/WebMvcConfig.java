package com.example.demofakecloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebMvcConfig implements WebMvcConfigurer {

  private final String allowedOrigins = "http://localhost:3000";

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // 设置允许跨域的路径
    registry.addMapping("/**")
        // 设置允许跨域请求的域名
        .allowedOriginPatterns("*")
        // 是否允许cookie
        .allowCredentials(true)
        // 设置允许的请求方式
        .allowedMethods("GET", "POST", "DELETE", "PUT")
        // 设置允许的header属性
        .allowedHeaders("*")
        // 跨域允许时间
        .maxAge(3600);

  }
}
