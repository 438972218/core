package com.xdcplus.ldap;


import cn.hutool.core.collection.CollectionUtil;
import com.xdcplus.ldap.config.Ldap2Properties;
import com.xdcplus.ldap.constants.LdapConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.CollectingAuthenticationErrorCallback;
import org.springframework.ldap.core.LdapTemplate;

import java.util.List;

/**
 * ldap 工具类
 * @author Rong.Jia
 * @date 2021/05/26
 */
public class LdapTemplateUtils {

    private static final Logger logger = LoggerFactory.getLogger(LdapTemplateUtils.class);

    private final LdapTemplate ldapTemplate;
    private final Ldap2Properties ldap2Properties;

    public LdapTemplateUtils(LdapTemplate ldapTemplate, Ldap2Properties ldap2Properties) {
        this.ldapTemplate = ldapTemplate;
        this.ldap2Properties = ldap2Properties;
    }

    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }


    /**
     * 根据名称查询Ldap信息
     *
     * @param name   名字
     * @param tClass 目标对象Class
     * @return {@link T} Ldap信息
     */
    public <T> T findLdapByName(String name, Class<T> tClass){

        List<T> ldapList = ldapTemplate.search(LdapConstant.OU + ldap2Properties.getOu(),
                "(&(objectclass=" + ldap2Properties.getObjectClasses() + ")(name=" + name + "))",
                (AttributesMapper<T>) attributes -> LdapBeanUtils.copyProperties(attributes, tClass));

        if (CollectionUtil.isNotEmpty(ldapList)) {
            return ldapList.get(0);
        }

        return null;
    }

    /**
     * 根据CN查询Ldap信息
     *
     * @param cn   CN
     * @param tClass 目标对象Class
     * @return {@link T} Ldap信息
     */
    public <T> T findLdapByCn(String cn, Class<T> tClass){

        List<T> ldapList = ldapTemplate.search(LdapConstant.OU + ldap2Properties.getOu(),
                "(&(objectclass=" + ldap2Properties.getObjectClasses() + ")(cn=" + cn + "))",
                (AttributesMapper<T>) attributes -> LdapBeanUtils.copyProperties(attributes, tClass));

        if (CollectionUtil.isNotEmpty(ldapList)) {
            return ldapList.get(0);
        }

        return null;
    }

    /**
     * 根据域名查询Ldap信息
     *
     * @param domainName   域名
     * @param tClass 目标对象Class
     * @return {@link T} Ldap信息
     */
    public <T> T findLdapByDomainName(String domainName, Class<T> tClass){

        List<T> ldapList = ldapTemplate.search(LdapConstant.OU + ldap2Properties.getOu(),
                "(&(objectclass=" + ldap2Properties.getObjectClasses() + ")(sAMAccountName=" + domainName + "))",
                (AttributesMapper<T>) attributes -> LdapBeanUtils.copyProperties(attributes, tClass));

        if (CollectionUtil.isNotEmpty(ldapList)) {
            return ldapList.get(0);
        }

        return null;
    }

    /**
     * 查询所有Ldap信息
     *
     * @param tClass 目标对象Class
     * @return {@link T} Ldap信息
     */
    public <T> List<T> findAll(Class<T> tClass){

        return ldapTemplate.search(LdapConstant.OU + ldap2Properties.getOu(),
                "(&(objectclass=" + ldap2Properties.getObjectClasses() + "))",
                (AttributesMapper<T>) attributes -> LdapBeanUtils.copyProperties(attributes, tClass));

    }

    /**
     * 认证Ldap 用户
     *
     * @param account 域控账号
     * @param password 密码
     * @return 是否认证成功  false:失败，true:成功
     */
    public boolean authenticate(String account, String password) {

        CollectingAuthenticationErrorCallback errorCallback = new CollectingAuthenticationErrorCallback();

        boolean authenticate = ldapTemplate.authenticate(LdapConstant.OU + ldap2Properties.getOu(),
                "(&(objectclass=" + ldap2Properties.getObjectClasses() + ")(sAMAccountName=" + account + "))",
                password, errorCallback);

        if (!authenticate) {
            logger.error("authenticate {}",  String.format("account : %s Authentication failed", account));
        }

        return authenticate;
    }



















}
