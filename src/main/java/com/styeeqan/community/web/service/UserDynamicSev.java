package com.styeeqan.community.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.mapper.*;
import com.styeeqan.community.pojo.po.*;
import com.styeeqan.community.pojo.vo.UserDynamicVo;
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

    @Autowired
    private UserMapper userMapper;

    public List<UserDynamicVo> getDynamicList(String homepageId) {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<UserDynamic> userDynamicQueryWrapper = new QueryWrapper<>();
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();

        User userDB = userMapper.selectOne(userQueryWrapper.eq("homepage_id", homepageId));

        List<UserDynamic> dynamicList = userDynamicMapper.selectList(userDynamicQueryWrapper.eq("create_user", userDB.getAccount()));

        return dynamicList.stream().map(dynamic -> {

            UserDynamicVo dynamicVO = new UserDynamicVo();

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
                userInfoQueryWrapper.clear();
                UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper.eq("account", comment.getCreateUser()));
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
