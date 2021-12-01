package com.yeexang.community.common.http.response;

import com.yeexang.community.common.constant.ServerStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yeeq
 * @date 2021/11/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SevFuncResult {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 状态码
     */
    private ServerStatusCode statusCode;
}
