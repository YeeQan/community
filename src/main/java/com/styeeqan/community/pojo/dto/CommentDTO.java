package com.styeeqan.community.pojo.dto;

import com.styeeqan.community.common.annotation.group.comment.Publish;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {

    @NotBlank(groups = {Publish.class}, message = "参数为空")
    private String parentId;

    @NotBlank(groups = {Publish.class}, message = "内容不能为空")
    @Size(min = 1, max = 500, groups = {Publish.class}, message = "内容不能超过500个字符")
    private String commentContent;
}
