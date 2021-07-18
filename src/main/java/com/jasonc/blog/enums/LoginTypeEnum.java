package com.jasonc.blog.enums;

/**
 * @Program: blog
 * @Package: com.jasonc.blog
 * @ClassName: LoginTypeEnum
 * @Author: Jason Chan
 * @Description: 登录方式枚举
 */
public enum LoginTypeEnum {
    /**
     * 邮箱登录
     */
    EMAIL(0, "邮箱登录"),
    /**
     * QQ登录
     */
    QQ(1, "QQ登录"),
    /**
     * github登录
     */
    GITHUB(2, "github登录");

    /**
     * 登录方式
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

    LoginTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
