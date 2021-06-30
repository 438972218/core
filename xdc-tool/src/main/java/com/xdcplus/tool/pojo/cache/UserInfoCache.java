package com.xdcplus.tool.pojo.cache;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 * @date 2021/05/21 16:22
 * @author Rong.Jia
 */
@Data
public class UserInfoCache implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 名称（昵称或者真实姓名）
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 数据来源类型，1- 系统添加, 2- ldap同步
     * ，默认 1
     */
    private Byte sourceType;

    /**
     * 邮箱
     */
    private String email;

    /**
     *  角色主键
     */
    private Long roleId;

    /**
     * 是否分配 0: 未分配，1: 分配
     */
    private Byte allocated;

    /**
     * 有效期,  -1--> 永久
     */
    private Long validity;

    /**
     * 用户 -- 角色 ： 1对多
     * 立即从数据库中进行加载数据;
     */
    private Role role;

    /**
     * 账号状态, 0已过期，1启用，-1禁用
     */
    private Byte status;

    /**
     *  组织id
     */
    private Long groupId;

    /**
     * 用户级别 -1：系统管理员，1：组织用户
     */
    private Byte userLevel;

    @Data
    public static class Role {

        /**
         * 角色ID
         */
        private Long id;

        private Byte status;

        /**
         *  权限资源列表 数组结构
         */
        private List<Permission> permissions;

        /**
         * 角色类型名
         */
        private String typeName;

        /**
         * 角色标识
         */
        private String role;

        /**
         * 角色名
         */
        private String name;

        /**
         * 权限ids
         */
        private List<Long> permissionIds;

        /**
         *  角色类型id
         */
        private Long typeId;

        @Data
        public static class Permission {

            /**
             * 权限
             */
            private Long id;

            /**
             *  父级id (若为顶级：0)
             */
            private Long parentId;

            /**
             * 资源类型 [menu|button]
             */
            private String resourceType;

            /**
             * 请求方式
             */
            private String method;

            /**
             * 资源名称
             */
            private String name;

            /**
             * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
             */
            private String permission;

            /**
             *  路由地址
             */
            private String route;

            /**
             *  图标
             */
            private String icon;

            /**
             *  排序
             */
            private Integer sort;

            /**
             *  是否隐藏（0:隐藏，1：显示）
             */
            private Byte hide;


        }



    }




}
