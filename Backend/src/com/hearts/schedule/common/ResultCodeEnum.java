package com.hearts.schedule.common;

/**
 * 业务含义和状态码对应关系的枚举
 */
public enum ResultCodeEnum
{
    // 枚举对象，调用下方的私有构造器
    SUCCESS(200, "success"),
    USERNAME_ERROR(501, "usernameError"),
    PASSWORD_ERROR(503, "passwordError"),
    NOTLOGIN(504, "notlogin"),
    USERNAME_USED(505, "usernameUsed");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public Integer getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }
}
