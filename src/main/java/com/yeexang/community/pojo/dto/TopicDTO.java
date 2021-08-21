package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import com.yeexang.community.pojo.po.Topic;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
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
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Boolean essentialStatus;
    private Boolean recommendedStatus;
    private String lastCommentTime;
    private String createTime;
    private String createUser;
    private String createUserName;

    private List<CommentDTO> commentDTOList;

    @Override
    public BasePO toPO() {
        Topic topic = new Topic();
        topic.setTopicId(topicId);
        topic.setTopicTitle(topicTitle);
        topic.setTopicContent(topicContent);
        topic.setSection(section);
        topic.setCommentCount(commentCount);
        topic.setViewCount(viewCount);
        topic.setLikeCount(likeCount);
        topic.setEssentialStatus(essentialStatus);
        topic.setRecommendedStatus(recommendedStatus);
        if (StringUtils.isEmpty(lastCommentTime)) {
            topic.setLastCommentTime(null);
        } else {
            try {
                topic.setLastCommentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastCommentTime));
            } catch (ParseException e) {
                log.error("TopicDTO toPO errorMsg: {}", e.getMessage());
            }
        }
        if (StringUtils.isEmpty(createTime)) {
            topic.setCreateTime(null);
        } else {
            try {
                topic.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime));
            } catch (ParseException e) {
                log.error("TopicDTO toPO errorMsg: {}", e.getMessage());
            }
        }
        topic.setCreateUser(createUser);
        return topic;
    }
}
