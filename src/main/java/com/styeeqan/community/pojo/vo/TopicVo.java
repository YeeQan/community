package com.styeeqan.community.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TopicVo {

    private String id;

    private String topicTitle;

    private String topicContent;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    private String createUserName;

    private String createUserHomepageId;

    private Integer commentCount;

    private Integer viewCount;

    private String createUserHeadPortrait;

    private PageVo<CommentVo> commentPageVo;

    private List<TagVo> tagVoList;
}
