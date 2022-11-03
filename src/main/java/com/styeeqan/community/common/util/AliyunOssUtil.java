package com.styeeqan.community.common.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.exception.CustomizeException;
import com.styeeqan.community.mapper.UserInfoMapper;
import com.styeeqan.community.pojo.po.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

/**
 * @author yeeq
 * @date 2021/12/11
 */
@Component
public class AliyunOssUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    private static OSS ossClient;

    private OSS getOssClient() {
        if (ossClient == null) {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        }
        return ossClient;
    }

    /**
     * 上传文件
     */
    public Optional<String> upload(String key, InputStream inputStream) {

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream);

        getOssClient().putObject(putObjectRequest);

        Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return Optional.ofNullable(url.toString());
        }

        return Optional.empty();
    }
}
