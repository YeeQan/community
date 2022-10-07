package com.styeeqan.community.common.http.response;

import com.google.common.collect.Lists;
import com.styeeqan.community.common.constant.ServerStatusCode;
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

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setData(T data) {
        this.data = Lists.newArrayList(data);
    }

    public ResponseEntity(ServerStatusCode serverStatusCode) {
        this.code = serverStatusCode.getCode();
        this.description = serverStatusCode.getDesc();
    }

    public ResponseEntity(T data) {
        this.code = ServerStatusCode.SUCCESS.getCode();
        this.description = ServerStatusCode.SUCCESS.getDesc();
        this.data = new ArrayList<>();
        this.data.add(data);
    }

    public ResponseEntity(List<T> data) {
        this.code = ServerStatusCode.SUCCESS.getCode();
        this.description = ServerStatusCode.SUCCESS.getDesc();
        this.data = data;
    }
}
