package com.styeeqan.community.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.mapper.CommentMapper;
import com.styeeqan.community.mapper.TopicMapper;
import com.styeeqan.community.mapper.UserDynamicMapper;
import com.styeeqan.community.mapper.UserInfoMapper;
import com.styeeqan.community.pojo.po.Comment;
import com.styeeqan.community.pojo.po.Topic;
import com.styeeqan.community.pojo.po.UserDynamic;
import com.styeeqan.community.pojo.po.UserInfo;
import com.styeeqan.community.pojo.vo.UserDynamicVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserDynamicSev {

    @Autowired
    private UserDynamicMapper userDynamicMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    public List<UserDynamicVO> getDynamicList(String account) {

        List<UserDynamic> dynamicList = userDynamicMapper.selectList(new QueryWrapper<UserDynamic>().eq("create_user", account));

        return dynamicList.stream().map(dynamic -> {

            UserDynamicVO dynamicVO = new UserDynamicVO();

            String type = dynamic.getType();
            String targetId = dynamic.getTargetId();
            String sourceId = dynamic.getSourceId();

            if (CommonField.PUBLISH_DYNAMIC_TYPE.equals(type)) {
                Topic topic = topicMapper.selectById(targetId);
                dynamicVO.setTargetContent(topic.getTopicTitle());
                dynamicVO.setSourceContent(topic.getTopicContent());
            } else if (CommonField.REPLY_TOPIC_DYNAMIC_TYPE.equals(type)) {
                Topic topic = topicMapper.selectById(targetId);
                Comment comment = commentMapper.selectById(sourceId);
                dynamicVO.setTargetContent(topic.getTopicTitle());
                dynamicVO.setSourceContent(comment.getCommentContent());
                UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", comment.getCreateUser()));
                if (userInfo != null) {
                    dynamicVO.setSourceCreateUsername(userInfo.getUsername());
                }
            }

            dynamicVO.setType(type);
            dynamicVO.setCreateTime(dynamic.getCreateTime());
            dynamicVO.setTargetId(targetId);

            return dynamicVO;
        }).collect(Collectors.toList());
    }
}
