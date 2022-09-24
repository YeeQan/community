package com.styeeqan.community.pojo.dto;

import com.styeeqan.community.common.annotation.group.topic.Publish;
import com.styeeqan.community.common.annotation.group.topic.Visit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TopicDTO {

    private int pageNum;
    private int pageSize;

    @NotBlank(groups = {Visit.class}, message = "参数为空")
    private String topicId;

    @NotBlank(groups = {Publish.class}, message = "标题不能为空")
    @Size(min = 1, max = 20, groups = {Publish.class}, message = "标题不能超过20个字符")
    private String topicTitle;

    @NotBlank(groups = {Publish.class}, message = "内容不能为空")
    @Size(min = 1, max = 1000, groups = {Publish.class}, message = "内容不能超过1000个字符")
    private String topicContent;
}
