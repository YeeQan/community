package com.styeeqan.community.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVo {

    private String commentId;

    private String commentContent;

    private String createUsername;

    private String createUserHomepageId;

    private String headPortrait;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    private String parentUserName;

    private String parentId;

    private String replyUsername;

    private String replyHomepageId;

    private String type;

    private List<CommentVo> commentVoList;
}
