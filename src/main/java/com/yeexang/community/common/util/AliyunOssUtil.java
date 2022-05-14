package com.yeexang.community.common.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.yeexang.community.common.http.response.AliyunOssResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * AliyunOss 对象存储工具类
 *
 * @author yeeq
 * @date 2021/11/4
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssUtil {

    /**
     * 阿里云 oss 站点
     */
    private String endpoint;

    /**
     * 阿里云 oss 公钥
     */
    private String accessKeyId;

    /**
     * 阿里云 oss 私钥
     */
    private String accessKeySecret;

    /**
     * 阿里云 oss 存储空间名
     */
    private String bucketName;

    /**
     * AliyunOss 工具客户端
     */
    private volatile OSSClient ossClient;

    /**
     * 单例模式
     * 初始化 AliyunOss 客户端
     */
    private void initOSSClient() {
        if (ossClient == null) {
            synchronized (AliyunOssUtil.class) {
                if (ossClient == null) {
                    ossClient = new OSSClient(endpoint,
                            new DefaultCredentialProvider(accessKeyId, accessKeySecret),
                            new ClientConfiguration());
                }
            }
        }
    }

    /**
     * 上传用户头像图片到 OSS
     *
     * @param file     file
     * @param account account
     * @return AliyunOssResult
     */
    public AliyunOssResult uploadHeadPortrait(MultipartFile file, String account) {
        AliyunOssResult aliyunOssResult;
        try {
            // 获取文件类型
            String fileType = file.getContentType();
            // 判断文件类型是否为 jpg、jpeg 或 png
            if ("image/jpeg".equals(fileType) || "image/jpg".equals(fileType) || "image/png".equals(fileType)) {
                // 头像图片大小控制在 2M 以内
                long size = file.getSize();
                if (size > 1024 * 2 * 1024 || size < 1024 * 2) {
                    aliyunOssResult = new AliyunOssResult(false, null, null, null, "用户头像图片尺寸不合适,范围10kb-2M");
                } else {
                    // 获取流
                    InputStream inputStream = file.getInputStream();
                    // 上传 oss
                    aliyunOssResult = upload(inputStream, fileType, "static/headPortrait/" + account);
                }
            } else {
                aliyunOssResult = new AliyunOssResult(false, null, null, null, "用户头像图片格式错误");
            }
        } catch (Exception e) {
            log.error("AliyunOssUtil uploadHeadPortrait errorMsg: {}", e.getMessage(), e);
            aliyunOssResult = new AliyunOssResult(false, null, null, null, "上传用户头像到 oss 失败");
        }
        return aliyunOssResult;
    }


    /**
     * 上传 oss
     *
     * @param input    input
     * @param fileType fileType
     * @param fileName fileName
     * @return AliyunOssResult
     */
    public AliyunOssResult upload(InputStream input, String fileType, String fileName) {
        AliyunOssResult aliyunOssResult;
        try {
            // 初始化 Oss 工具客户端
            initOSSClient();
            // 创建上传 Object 的 Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传内容类型
            meta.setContentType(fileType);
            // 被下载时网页的缓存行为
            meta.setCacheControl("no-cache");
            // 创建上传请求
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, input, meta);
            // 上传文件
            ossClient.putObject(request);
            // 获取上传成功的文件地址
            String ossUrl = getOssUrl(fileName);
            // 返回结果集
            aliyunOssResult = new AliyunOssResult(true, fileName, ossUrl, null, "上传 oss 成功");
        } catch (Exception e) {
            log.error("AliyunOssUtil upload errorMsg: {}", e.getMessage(), e);
            aliyunOssResult = new AliyunOssResult(false, null, null, null, "上传 oss 失败");
        }
        return aliyunOssResult;
    }

    /**
     * 从 oss 下载
     *
     * @param fileName fileName
     * @return AliyunOssResult
     */
    public AliyunOssResult download(String fileName) {
        AliyunOssResult aliyunOssResult;
        try {
            // 初始化 Oss 工具客户端
            initOSSClient();
            // 下载 OSS 文件
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
            OSSObject ossObject = ossClient.getObject(getObjectRequest);
            // 获取数据流
            InputStream inputStream = ossObject.getObjectContent();
            // 获取下载成功的文件地址
            String ossUrl = getOssUrl(fileName);
            // 返回结果集
            aliyunOssResult = new AliyunOssResult(true, fileName, ossUrl, inputStream, "从 oss 下载成功");
        } catch (Exception e) {
            log.error("AliyunOssUtil download errorMsg: {}", e.getMessage(), e);
            aliyunOssResult = new AliyunOssResult(false, null, null, null, "从 oss 下载失败");
        }
        return aliyunOssResult;
    }

    /**
     * 根据文件名生成文件的访问地址
     *
     * @param fileName fileName
     * @return String
     */
    private String getOssUrl(String fileName) {
        // 设置 URL 过期时间为 10 年  3600 * 1000 * 24 * 365 * 10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
        return url.toString();
    }
}
