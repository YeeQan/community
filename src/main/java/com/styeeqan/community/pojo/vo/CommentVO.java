package com.styeeqan.community.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVO {

    private String commentId;

    private String commentContent;

    private String createUsername;

    private String headPortrait;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    private List<CommentVO> commentVOList;
}
