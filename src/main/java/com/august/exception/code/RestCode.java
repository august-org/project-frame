package com.august.exception.code;

public enum RestCode implements RestCodeInterface {

    SUCCESS(0,"成功"),
    SYSTEM_ERROR(5000001,"系统异常"),
    DATA_ERROR(4000001,"数据异常"),
    VALIDATOR_ERROR(4000002,"参数校验异常"),
    ACCOUNT_ERROR(4000003,"该账号不存在，请先注册"),
    ACCOUNT_LOCK(4000004,"该账号已被锁定，请联系管理员"),
    ACCOUNT_PASSWORD_ERROR(4000005,"密码验证错误，请重新登录"),
    TOKEN_NOT_NULL(4010001,"认证token不能为空，请重新登录获取"),
    AUTHORIZATION_TOKEN_ERROR(4010001,"token认证失败，请重新登录获取最新的token"),
    ACCOUNT_LOCKED(4010001,"该账号已被锁定，请联系管理员"),
    ACCOUNT_HAS_DELETED_ERROR(4010001,"该账号已被删除，请联系管理员"),
    TOKEN_PAST_DUE(4010002,"token失效，请刷新token"),
    NO_PERMISSION(4030001,"没有访问权限该资源"),
    ;

    private final int code;
    private final String msg;

    RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
