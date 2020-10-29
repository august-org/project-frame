package com.august.constant;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/15
 */
public class Constant {
    /**
     * 用户名称key
     */
    public static final String JWT_USER_NAME = "jwt-user-name-key";
    /**
     * 角色信息key
     */
    public static final String ROLES_INFOS_KEY = "roles-infos-key";
    /**
     * 权限信息key
     */
    public static final String PERMISSIONS_INFOS_KEY = "permissions-infos-key";
    /**
     * 业务访问token
     */
    public static final String ACCESS_TOKEN = "authorization";
    /**
     * 主动去刷新token key【适用场景：修改用户角色/权限之后刷新token…】
     */
    public static final String JWT_REFRESH_KEY = "jwt-refresh-key_";
    /**
     * 标记用户是否已删除
     */
    public static final String DELETED_USER_KEY = "deleted-user-key_";
    /**
     * 标记用户是否已删除
     */
    public static final String ACCOUNT_LOCK_KEY = "account-lock-key_";
    /**
     * 用户鉴权缓存key
     */
    public static final String IDENTIFY_CACHE_KEY = "shiro-cache:authc";
}
