package com.styeeqan.community.pojo.dto;

import com.styeeqan.community.common.annotation.group.comment.CommentPage;
import com.styeeqan.community.common.annotation.group.comment.CommentPublish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel("评论dto")
public class CommentDto {

    @ApiModelProperty("页码")
    private int pageNum;

    @ApiModelProperty("页数")
    private int pageSize;

    @ApiModelProperty("父Id")
    @NotBlank(groups = {CommentPublish.class, CommentPage.class}, message = "参数为空")
    private String parentId;

    @ApiModelProperty("回复对象Id")
    private String replyTaId;

    @ApiModelProperty("评论内容")
    @NotBlank(groups = {CommentPublish.class}, message = "内容不能为空")
    @Size(min = 1, max = 500, groups = {CommentPublish.class}, message = "内容不能超过500个字符")
    private String commentContent;

    @ApiModelProperty("评论类型")
    @NotBlank(groups = {CommentPublish.class, CommentPage.class}, message = "评论类型不能为空")
    private String type;
}
