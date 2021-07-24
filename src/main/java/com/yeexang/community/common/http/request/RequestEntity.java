package com.yeexang.community.common.http.request;

import lombok.Data;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Data
public class RequestEntity<T> {

    List<T> data;
}
