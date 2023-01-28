package com.styeeqan.community.common.constant;

/**
 * 服务状态码工具类
 *
 * @author yeeq
 * @date 2021/7/24
 */
public enum ServerStatusCode {

    SUCCESS("2000", "成功"),

    /**
     * 错误码
     */
    UNKNOWN("4000", "未知异常"),
    PARAM_ERROR("4001", "请求参数错误"),
    ACCOUNT_EXIST("4007", "账号已存在"),
    USERNAME_EXIST("4008", "昵称已存在"),
    ACCOUNT_NOT_EXIST("4009", "账号不存在或密码错误"),
    IMAGE_TYPE_ERROR("4010", "图片类型错误"),
    IMAGE_SIZE_ERROR("4011", "图片大小错误"),
    GET_PUBLIC_KEY_ERROR("4012", "获取公钥失败"),
    UNAUTHORIZED("4020", "请先完成登录操作"),
    PASSWORD_ERROR("4024", "密码错误"),
    USER_VISIT_FREQUENTLY("4025", "访问太频繁，请稍后再试")
    ;

    private final String code;
    private final String desc;

    ServerStatusCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
