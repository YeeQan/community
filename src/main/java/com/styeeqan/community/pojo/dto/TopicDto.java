package com.styeeqan.community.pojo.dto;

import com.styeeqan.community.common.annotation.group.topic.TopicPublish;
import com.styeeqan.community.common.annotation.group.topic.TopicVisit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel("帖子dto")
public class TopicDto {

    @ApiModelProperty("页码")
    private int pageNum;

    @ApiModelProperty("页数")
    private int pageSize;

    @ApiModelProperty("评论页码")
    private int commentPageNum;

    @ApiModelProperty("评论页数")
    private int commentPageSize;

    @ApiModelProperty("帖子主键")
    @NotBlank(groups = {TopicVisit.class}, message = "参数为空")
    private String topicId;

    @ApiModelProperty("帖子标题")
    @NotBlank(groups = {TopicPublish.class}, message = "标题不能为空")
    @Size(min = 1, max = 20, groups = {TopicPublish.class}, message = "标题不能超过20个字符")
    private String topicTitle;

    @ApiModelProperty("帖子内容")
    @NotBlank(groups = {TopicPublish.class}, message = "内容不能为空")
    @Size(min = 1, max = 1000, groups = {TopicPublish.class}, message = "内容不能超过1000个字符")
    private String topicContent;

    @ApiModelProperty("帖子标签")
    @NotBlank(groups = {TopicPublish.class}, message = "标签不能为空")
    private String tags;

    @ApiModelProperty("排序方式")
    private String sortOrder;
}
