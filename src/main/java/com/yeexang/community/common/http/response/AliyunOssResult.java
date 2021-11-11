package com.yeexang.community.common.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * 阿里云 Oss 调用结果集
 *
 * @author yeeq
 * @date 2021/11/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliyunOssResult {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 文件名（如果使用自定义文件路径，会返回完整的路径+文件名，例：cf/abc.png）
     */
    private String fileName;

    /**
     * 操作成功返回 url
     */
    private String url;

    /**
     * 数据流
     */
    private InputStream inputStream;

    /**
     * 提示信息
     */
    private String msg;
}