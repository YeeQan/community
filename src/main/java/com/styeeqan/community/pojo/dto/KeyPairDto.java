package com.styeeqan.community.pojo.dto;

import lombok.Data;

@Data
public class KeyPairDto {

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;
}
