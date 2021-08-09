package com.yeexang.community.common.http.response;

import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.pojo.dto.NotificationDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseEntity<T> {

    private String code;

    private String description;

    private List<T> data;

    private List<NotificationDTO> notificationDTOList;

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
