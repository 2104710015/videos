package com.videos.course.getaway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2023-07-24 10:41
 */


@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class GetawayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(GetawayApplication.class);
        ConfigurableEnvironment environment = run.getEnvironment();
           log.info("getaway 服务启动-【成功】");
           log.info("getaway  请求地址\t http://127.0.0.1:{}",environment.getProperty("server.port"));
    }


    /**
     * 处理跨域的问题
     */
    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
           corsConfiguration.setAllowCredentials(Boolean.TRUE);
           corsConfiguration.addAllowedHeader("*");
           corsConfiguration.addAllowedOrigin("*");
           corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource
                = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
   return   new  CorsWebFilter(urlBasedCorsConfigurationSource);

    }
}
