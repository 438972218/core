package com.xdcplus.ldap.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性映射注解
 * @author Rong.Jia
 * @date 2021/05/26
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {

    /**
     * 属性字段名
     *
     * @return {@link String}
     */
    String name() default "";







}
