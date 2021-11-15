package com.yeexang.community.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;

/**
 * IP 工具类
 *
 * @author yeeq
 * @date 2021/11/14
 */
@Slf4j
@Component
public class IpUtil {

    /**
     * 获取当前请求的 ip
     *
     * @param request request
     * @return ip
     */
    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        try {
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                    // 根据网卡取本机配置的 IP
                    InetAddress inet;
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个 IP 为客户端真实 IP，多个 IP 按照 ',' 分割
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            log.error("IpUtil getIpAddr errorMsg: {}", e.getMessage(), e);
        }
        return ipAddress;
    }
}
