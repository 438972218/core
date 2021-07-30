package com.xdcplus.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Http 配置类
 *
 * @author Rong.Jia
 * @date 2021/01/28 16:18:22
 */
@Configuration
@ComponentScan("com.xdcplus.http")
@ConditionalOnClass({HttpClientPoolProperties.class})
@EnableConfigurationProperties({HttpClientPoolProperties.class})
public class HttpAutoConfiguration {

    @Bean
    public HttpFactoryBean hikCloudEyesFactory() {
        return new HttpFactoryBean();
    }


}
