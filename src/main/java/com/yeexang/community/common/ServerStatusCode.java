package com.yeexang.community.common;

/**
 * @author yeeq
 * @date 2021/7/24
 */
public enum ServerStatusCode {

    SUCCESS("2000", "成功"),

    /**
     * 错误码
     */
    UNKNOWN("4000", "未知异常"),
    ACCOUNT_EMPTY("4001", "账号不能为空"),
    ACCOUNT_FORMAT_ERROR("4002", "账号格式错误，必须由字母、数字、下划线组成，不能超过12位"),
    USERNAME_EMPTY("4003", "昵称不能为空"),
    USERNAME_FORMAT_ERROR("4004", "昵称格式错误，必须由字母、数字、下划线组成，不能超过12位"),
    PASSWORD_EMPTY("4005", "密码不能为空"),
    PASSWORD_FORMAT_ERROR("4006", "密码格式错误，必须由字母、数字、下划线组成，不能超过16位"),
    ACCOUNT_EXIST("4007", "账号已存在"),
    USERNAME_EXIST("4008", "昵称已存在"),
    ACCOUNT_NOT_EXIST("4009", "账号不存在或密码错误"),
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
