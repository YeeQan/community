package com.yeexang.community.common.http.response;

import com.yeexang.community.common.constant.ServerStatusCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用响应类
 *
 * @author yeeq
 * @date 2021/7/23
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseEntity<T> {

    /**
     * 响应码
     */
    private String code;

    /**
     * 详细信息
     */
    private String description;

    /**
     * 返回数据
     */
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

    public ResponseEntity(T data) {
        this.code = ServerStatusCode.SUCCESS.getCode();
        this.description = ServerStatusCode.SUCCESS.getDesc();
        this.data = new ArrayList<>();
        this.data.add(data);
    }
}
