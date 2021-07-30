package com.xdcplus.auto;

import cn.hutool.core.comparator.VersionComparator;
import com.xdcplus.tool.constants.NumberConstant;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 *  跨域配置
 * @date 2021/04/29 08:58
 * @author Rong.Jia
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig {

    private static final String SPRING_VERISON = "2.4.0";

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("Authorization,Origin, X-Requested-With, Content-Type, Accept,WWW-Authenticate"));
        config.setAllowCredentials(true);

        if (version()) {
            config.addAllowedOriginPattern("*");
        }else {
            config.addAllowedOrigin("*");
        }
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addExposedHeader("Location");

        source.registerCorsConfiguration("/**", config);
       FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

       bean.setOrder(0);
       return bean;
    }

    /**
     * 版本
     *
     * @return boolean
     */
    private boolean version() {
        String version = SpringBootVersion.getVersion();
        return VersionComparator.INSTANCE.compare(version, SPRING_VERISON) >= NumberConstant.ZERO;
    }




}
