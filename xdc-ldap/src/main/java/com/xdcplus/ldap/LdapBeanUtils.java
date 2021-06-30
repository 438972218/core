package com.xdcplus.ldap;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.xdcplus.ldap.annotations.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.Attributes;
import java.lang.reflect.Field;
import java.util.List;

/**
 * ldap bean 工具类
 *
 * @author Rong.Jia
 * @date 2021/05/26
 */
public class LdapBeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(LdapBeanUtils.class);

    /**
     * 复制属性
     * @param attributes 属性
     * @param tClass     t类
     * @return {@link T} 目标类型对象
     */
    public static <T> T copyProperties(Attributes attributes, Class<T> tClass) {

        if (ObjectUtil.isNull(attributes)) {
            return null;
        }

        List<Field> fieldList = CollectionUtil.newArrayList(ReflectUtil.getFields(tClass));

        T instance = ReflectUtil.newInstance(tClass);

        for (Field field : fieldList) {

            String fieldName = getFieldName(field);
            field.setAccessible(Boolean.TRUE);

            javax.naming.directory.Attribute attribute = attributes.get(fieldName);
            if (ObjectUtil.isNotNull(attribute)) {

                Object object = null;
                try {
                    object = attribute.get();
                } catch (Exception ignored) {
                }
                if (ObjectUtil.isNotNull(object)) {
                    try {
                        ReflectUtil.setFieldValue(instance, field, object);
                    }catch (Exception e) {
                        logger.error("copyProperties {}",
                                String.format("Field property : %s set error", fieldName));
                    }
                }
            }
        }

        return instance;
    }

    /**
     * 获取字段名称
     *
     * @param field 属性字段
     * @return {@link String} 字段名
     */
    private static String getFieldName(Field field) {

        String fieldName = field.getName();
        if (field.isAnnotationPresent(Attribute.class)) {
            fieldName = field.getAnnotation(Attribute.class).name();
        }

        return fieldName;
    }


}
