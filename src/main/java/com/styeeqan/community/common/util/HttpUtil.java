package com.styeeqan.community.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Optional;

@Slf4j
@Component
public class HttpUtil {

     public Optional<String> getJsonData(HttpServletRequest request) {
          InputStream in = null;
          ByteArrayOutputStream baos = null;
          try {
               in = request.getInputStream();
               baos = new ByteArrayOutputStream();
               byte[] bytes = new byte[128];
               int len;
               while ((len = in.read(bytes)) != -1) {
                    baos.write(bytes, 0, len);
               }
               String json = baos.toString();
               return StringUtils.isEmpty(json) ? Optional.empty() : Optional.of(json);
          } catch (Exception e) {
               log.info("Http请求信息转化Json失败", e);
               return Optional.empty();
          } finally {
               try {
                    if (in != null) {
                         in.close();
                    }
                    if (baos != null) {
                         baos.close();
                    }
               } catch (IOException e) {
                    log.info("Http请求信息转化Json失败", e);
               }
          }
     }
}
