package com.styeeqan.community.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserHomepageVO {

    private String userInfoId;

    private String username;

    private String headPortrait;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date joinTime;

    private Boolean self;
}
