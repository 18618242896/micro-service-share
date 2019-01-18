package com.share.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
@Configuration
public class ZuulConfig {

    /**
     * Zuul统一解决跨域问题
     * @return
     */
    @Bean
    public CorsFilter corsFilter(){

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");    // #允许访问的头信息,*表示全部
        corsConfiguration.addAllowedOrigin("*");    // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        corsConfiguration.addAllowedMethod("*");    // 允许提交请求的方法，*表示全部允许
        corsConfiguration.setMaxAge(18000L);        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        corsConfiguration.setAllowCredentials(true);    // 允许cookies跨域
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }


}
