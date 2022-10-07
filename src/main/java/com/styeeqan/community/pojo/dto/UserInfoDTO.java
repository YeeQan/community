package com.styeeqan.community.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoDTO extends BaseDTO {

    private String account;
    private String introduction;
    private String sex;
    private String company;
    private String position;
}
