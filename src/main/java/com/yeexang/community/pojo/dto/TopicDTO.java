package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yeeq
 * @date 2021/7/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TopicDTO extends BaseDTO {

    private String topicId;
    private String topicTitle;
    private String topicContent;
    private String section;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Boolean essentialStatus;
    private Boolean recommendedStatus;
    private String lastCommentTime;

    @Override
    public BasePO toPO() {
        Topic topic = new Topic();
        try {
            topic.setTopicId(topicId);
            topic.setTopicTitle(topicTitle);
            topic.setTopicContent(topicContent);
            topic.setSection(section);
            topic.setCommentCount(commentCount);
            topic.setViewCount(viewCount);
            topic.setLikeCount(likeCount);
            topic.setEssentialStatus(essentialStatus);
            topic.setRecommendedStatus(recommendedStatus);
            topic.setLastCommentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastCommentTime));
        } catch (Exception e) {
            // 抛出通用异常。。
        }
        return topic;
    }
}
