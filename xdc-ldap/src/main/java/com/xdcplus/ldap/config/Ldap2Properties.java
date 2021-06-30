package com.xdcplus.ldap.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * ldap属性
 *
 * @author Rong.Jia
 * @date 2021/05/26
 */
@Primary
@Data
@ConfigurationProperties(prefix = "spring.ldap")
public class Ldap2Properties {

    /**
     * ldap服务器的ip和端口
     */
    private String[] urls;

    /**
     * 唯一标识
     */
    private String base;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 对象类
     */
    private String objectClasses;

    /**
     * base name
     */
    private String ou;


}
