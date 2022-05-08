package com.yeexang.community.common.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 键值对结果集
 *
 * @author yeeq
 * @date 2022/5/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueResult {

    private String key;

    private String value;
}
