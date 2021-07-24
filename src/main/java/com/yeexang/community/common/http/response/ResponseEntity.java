package com.yeexang.community.common.http.response;

import com.yeexang.community.common.ServerStatusCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Data
@NoArgsConstructor
public class ResponseEntity<T> {

    private String code;

    private String description;

    private List<T> data;

    public ResponseEntity(ServerStatusCode serverStatusCode) {
        this.code = serverStatusCode.getCode();
        this.description = serverStatusCode.getDesc();
    }

    public ResponseEntity(List<T> data) {
        this.code = ServerStatusCode.SUCCESS.getCode();
        this.description = ServerStatusCode.SUCCESS.getDesc();
        this.data = data;
    }
}
