package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Topic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 帖子 DTO
 *
 * @author yeeq
 * @date 2021/7/25
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class TopicDTO extends BaseDTO {

    private String topicId;
    private String topicTitle;
    private String topicContent;
    private String section;
    private String createUser;
    private String updateUser;

    @Override
    public Optional<BasePO> toPO() {
        Topic topic;
        try {
            topic = new Topic();
            topic.setTopicId(topicId);
            topic.setTopicTitle(topicTitle);
            topic.setTopicContent(topicContent);
            topic.setSection(section);
            topic.setCreateUser(createUser);
            topic.setUpdateUser(updateUser);
        } catch (Exception e) {
            log.error("TopicDTO toPO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(topic);
    }
}
