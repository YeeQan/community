package com.styeeqan.community.pojo.dto;

import com.styeeqan.community.common.annotation.group.topic.TopicPublish;
import com.styeeqan.community.common.annotation.group.topic.TopicVisit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class TopicDTO extends BaseDTO  {

    private int pageNum;
    private int pageSize;

    @NotBlank(groups = {TopicVisit.class}, message = "参数为空")
    private String topicId;

    @NotBlank(groups = {TopicPublish.class}, message = "标题不能为空")
    @Size(min = 1, max = 20, groups = {TopicPublish.class}, message = "标题不能超过20个字符")
    private String topicTitle;

    @NotBlank(groups = {TopicPublish.class}, message = "内容不能为空")
    @Size(min = 1, max = 1000, groups = {TopicPublish.class}, message = "内容不能超过1000个字符")
    private String topicContent;

    @NotBlank(groups = {TopicPublish.class}, message = "标签不能为空")
    private String tags;
}
