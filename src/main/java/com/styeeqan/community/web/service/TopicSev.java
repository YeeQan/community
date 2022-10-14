package com.styeeqan.community.web.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.CommonUtil;
import com.styeeqan.community.mapper.*;
import com.styeeqan.community.pojo.po.*;
import com.styeeqan.community.pojo.vo.CommentVO;
import com.styeeqan.community.pojo.vo.PageVO;
import com.styeeqan.community.pojo.vo.TagVO;
import com.styeeqan.community.pojo.vo.TopicVO;
import com.styeeqan.community.task.UserDynamicTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TopicSev {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    /**
     * 获取帖子分页
     *
     * @param pageNum 页码
     * @param pageSize 页数
     * @return PageVO<TopicVO>
     */
    public PageVO<TopicVO> getPage(Integer pageNum, Integer pageSize) {

        PageVO<TopicVO> pageVO = new PageVO<>();

        // 是否开启分页
        if (pageNum > 0 && pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Topic> topicList = topicMapper.selectList(null);
        PageInfo<Topic> pageInfo = new PageInfo<>(topicList);

        pageVO.setPageNum(pageInfo.getPageNum());
        pageVO.setPages(pageInfo.getPages());
        pageVO.setNavigatepageNums(pageInfo.getNavigatepageNums());

        List<TopicVO> topicVOList = topicList.stream()
                .map(po -> {
                    TopicVO vo = new TopicVO();
                    vo.setId(po.getId());
                    vo.setTopicTitle(po.getTopicTitle());
                    vo.setCreateTime(po.getCreateTime());
                    vo.setCommentCount(po.getCommentCount());
                    vo.setViewCount(po.getViewCount());
                    UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", po.getCreateUser()));
                    if (userInfo != null) {
                        vo.setCreateUserName(userInfo.getUsername());
                        vo.setCreateUserHeadPortrait(userInfo.getHeadPortrait());
                    }
                    User user = userMapper.selectById(po.getCreateUser());
                    if (user != null) {
                        vo.setCreateUserHomepageId(user.getHomepageId());
                    }
                    String tags = po.getTags();
                    if (!StringUtils.isEmpty(tags)) {
                        String[] split = tags.split(",");
                        List<Tag> tagList = tagMapper.selectList(new QueryWrapper<Tag>().in("id", Arrays.asList(split)));
                        vo.setTagVOList(tagList.stream().map(tag -> {
                            TagVO tagVO = new TagVO();
                            tagVO.setId(tag.getId());
                            tagVO.setName(tag.getName());
                            return tagVO;
                        }).collect(Collectors.toList()));
                    }
                    return vo;
                })
                .collect(Collectors.toList());

        pageVO.setList(topicVOList);
        return pageVO;
    }

    public TopicVO visit(String topicId) {

        TopicVO topicVO = new TopicVO();

        Topic topic = topicMapper.selectById(topicId);
        if (topic != null) {
            topicVO.setId(topic.getId());
            topicVO.setTopicTitle(topic.getTopicTitle());
            topicVO.setTopicContent(topic.getTopicContent());
            topicVO.setCreateTime(topic.getCreateTime());
            topicVO.setViewCount(topic.getViewCount());
            topicVO.setCommentCount(topic.getCommentCount());
            // 设置用户名和头像
            UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", topic.getCreateUser()));
            topicVO.setCreateUserName(userInfo.getUsername());
            topicVO.setCreateUserHeadPortrait(userInfo.getHeadPortrait());
            // 设置用户信息
            User user = userMapper.selectById(topic.getCreateUser());
            topicVO.setCreateUserHomepageId(user.getHomepageId());
            // 设置标签
            String tags = topic.getTags();
            if (!StringUtils.isEmpty(tags)) {
                String[] split = tags.split(",");
                List<Tag> tagList = tagMapper.selectList(new QueryWrapper<Tag>().in("id", Arrays.asList(split)));
                topicVO.setTagVOList(tagList.stream().map(tag -> {
                    TagVO tagVO = new TagVO();
                    tagVO.setId(tag.getId());
                    tagVO.setName(tag.getName());
                    return tagVO;
                }).collect(Collectors.toList()));
            }
            // 设置该讨论下的所有评论
            List<Comment> commentList = commentMapper.selectList(new QueryWrapper<Comment>().eq("parent_id", topicId));
            // 一级评论
            List<CommentVO> commentVOList1 = commentList.stream().map(comment1 -> {
                CommentVO commentVO1 = new CommentVO();
                UserInfo info1 = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", comment1.getCreateUser()));
                commentVO1.setCreateUsername(info1.getUsername());
                commentVO1.setHeadPortrait(info1.getHeadPortrait());
                commentVO1.setCommentId(comment1.getId());
                commentVO1.setCommentContent(comment1.getCommentContent());
                commentVO1.setCreateTime(comment1.getCreateTime());
                User user1 = userMapper.selectById(comment1.getCreateUser());
                commentVO1.setCreateUserHomepageId(user1.getHomepageId());
                // 二级评论
                List<Comment> commentList2 = commentMapper.selectList(new QueryWrapper<Comment>().eq("parent_id", comment1.getId()));
                List<CommentVO> commentVOList2 = commentList2.stream().map(comment2 -> {
                    CommentVO commentVO2 = new CommentVO();
                    UserInfo info2 = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", comment2.getCreateUser()));
                    commentVO2.setCreateUsername(info2.getUsername());
                    commentVO2.setHeadPortrait(info2.getHeadPortrait());
                    commentVO2.setCommentId(comment2.getId());
                    commentVO2.setCommentContent(comment2.getCommentContent());
                    commentVO2.setCreateTime(comment2.getCreateTime());
                    User user2 = userMapper.selectById(comment2.getCreateUser());
                    commentVO2.setCreateUserHomepageId(user2.getHomepageId());
                    return commentVO2;
                }).collect(Collectors.toList());
                commentVO1.setCommentVOList(commentVOList2);
                return commentVO1;
            }).collect(Collectors.toList());

            topicVO.setCommentVOList(commentVOList1);

            // 阅读数加一
            topicMapper.incrViewCount(topicId);
        }
        return topicVO;
    }

    public TopicVO publish(String topicId, String topicTitle, String topicContent, String tags, String account) {

        TopicVO topicVO = new TopicVO();

        Topic topic = new Topic();
        topic.setId(StringUtils.isEmpty(topicId) ? commonUtil.randomCode() : topicId);
        topic.setTopicTitle(topicTitle);
        topic.setTopicContent(topicContent);
        topic.setTags(tags);
        topic.setCommentCount(0);
        topic.setViewCount(0);
        topic.setUpdateUser(account);
        topic.setUpdateTime(new Date());

        if (StringUtils.isEmpty(topicId)) {
            topic.setCreateUser(account);
            topic.setCreateTime(new Date());
            topicMapper.insert(topic);
        } else {
            topicMapper.updateById(topic);
        }

        // 生成动态放入Redis消息队列
        UserDynamicTask userDynamicTask = new UserDynamicTask();
        userDynamicTask.setType(CommonField.PUBLISH_DYNAMIC_TYPE);
        userDynamicTask.setTargetId(topic.getId());
        userDynamicTask.setSourceId(topic.getId());
        userDynamicTask.setCreateUser(account);
        userDynamicTask.setUpdateUser(account);
        redisUtil.pushListRightValue(RedisKey.USER_DYNAMIC_TASK_LIST, null, JSON.toJSONString(userDynamicTask));

        topicVO.setId(topic.getId());
        return topicVO;
    }
}
