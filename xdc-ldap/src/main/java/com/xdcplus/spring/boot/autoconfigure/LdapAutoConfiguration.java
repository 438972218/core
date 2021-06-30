package com.xdcplus.spring.boot.autoconfigure;


import com.xdcplus.ldap.LdapTemplateUtils;
import com.xdcplus.ldap.config.Ldap2Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * ldap自动配置
 * @author Rong.Jia
 * @date 2021/05/26
 */
@Configuration
@EnableConfigurationProperties(Ldap2Properties.class)
public class LdapAutoConfiguration {

    private final Ldap2Properties ldap2Properties;

    public LdapAutoConfiguration(Ldap2Properties ldap2Properties) {
        this.ldap2Properties = ldap2Properties;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrls(ldap2Properties.getUrls());
        ldapContextSource.setPassword(ldap2Properties.getPassword());
        ldapContextSource.setUserDn(ldap2Properties.getPassword());
        ldapContextSource.setBase(ldap2Properties.getBase());
        ldapContextSource.setAuthenticationSource(new AuthenticationSource() {
            @Override
            public String getPrincipal() {
                return ldap2Properties.getUsername();
            }
            @Override
            public String getCredentials() {
                return ldap2Properties.getPassword();
            }
        });

        ldapContextSource.afterPropertiesSet();

        return new LdapTemplate(ldapContextSource);
    }





    @Bean
    public LdapTemplateUtils ldapTemplateUtils () {
        return new LdapTemplateUtils(ldapTemplate(), ldap2Properties);
    }




}
