package com.styeeqan.community.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户 VO
 *
 * @author yeeq
 * @date 2021/11/21
 */
@Data
public class UserVo {

    private String username;

    private String headPortrait;

    private String homepageId;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

    private String sex;

    private String city;

    private String introduction;

    private String school;

    private String major;

    private String company;

    private String position;
}
