package com.styeeqan.community.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.CommonUtil;
import com.styeeqan.community.common.util.ThreadUtil;
import com.styeeqan.community.mapper.*;
import com.styeeqan.community.pojo.po.*;
import com.styeeqan.community.pojo.vo.CommentVo;
import com.styeeqan.community.pojo.vo.PageVo;
import com.styeeqan.community.pojo.vo.TagVo;
import com.styeeqan.community.pojo.vo.TopicVo;
import com.styeeqan.community.task.UserContributeDayRankTask;
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

    @Autowired
    private UserContributeMapper contributeMapper;

    @Autowired
    private ThreadUtil threadUtil;

    /**
     * 获取帖子分页
     */
    public PageVo<TopicVo> getPage(Integer pageNum, Integer pageSize, String sortOrder) {

        PageVo<TopicVo> pageVO = new PageVo<>();

        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();

        // 是否有排序条件,默认按最后一次评论时间倒序
        if (!StringUtils.isEmpty(sortOrder)) {
            setSortOrder(sortOrder, topicQueryWrapper);
        }

        // 是否开启分页
        if (pageNum > 0 && pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Topic> topicList = topicMapper.selectList(topicQueryWrapper);
        PageInfo<Topic> pageInfo = new PageInfo<>(topicList);

        pageVO.setPageNum(pageInfo.getPageNum());
        pageVO.setPages(pageInfo.getPages());
        pageVO.setNavigatepageNums(pageInfo.getNavigatepageNums());

        List<TopicVo> topicVoList = topicList.stream()
                .map(po -> {
                    TopicVo vo = new TopicVo();
                    vo.setId(po.getId());
                    vo.setTopicTitle(po.getTopicTitle());
                    vo.setCreateTime(po.getCreateTime());
                    vo.setCommentCount(po.getCommentCount());
                    vo.setViewCount(po.getViewCount());

                    userInfoQueryWrapper.clear();
                    UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper.eq("account", po.getCreateUser()));
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
                        tagQueryWrapper.clear();
                        List<Tag> tagList = tagMapper.selectList(tagQueryWrapper.in("id", Arrays.asList(split)));
                        vo.setTagVoList(tagList.stream().map(tag -> {
                            TagVo tagVO = new TagVo();
                            tagVO.setId(tag.getId());
                            tagVO.setName(tag.getName());
                            return tagVO;
                        }).collect(Collectors.toList()));
                    }
                    return vo;
                })
                .collect(Collectors.toList());

        pageVO.setData(topicVoList);
        return pageVO;
    }

    public TopicVo visit(String topicId, Integer commentPageNum, Integer commentPageSize) {

        TopicVo topicVO = new TopicVo();

        Topic topic = topicMapper.selectById(topicId);
        if (topic != null) {
            topicVO.setId(topic.getId());
            topicVO.setTopicTitle(topic.getTopicTitle());
            topicVO.setTopicContent(topic.getTopicContent());
            topicVO.setCreateTime(topic.getCreateTime());
            topicVO.setViewCount(topic.getViewCount());
            topicVO.setCommentCount(topic.getCommentCount());

            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
            QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();

            // 设置用户名和头像
            UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper.eq("account", topic.getCreateUser()));
            topicVO.setCreateUserName(userInfo.getUsername());
            topicVO.setCreateUserHeadPortrait(userInfo.getHeadPortrait());

            // 设置用户信息
            User user = userMapper.selectById(topic.getCreateUser());
            topicVO.setCreateUserHomepageId(user.getHomepageId());

            // 设置标签
            String tags = topic.getTags();
            if (!StringUtils.isEmpty(tags)) {
                String[] split = tags.split(",");
                List<Tag> tagList = tagMapper.selectList(tagQueryWrapper.in("id", Arrays.asList(split)));
                topicVO.setTagVoList(tagList.stream().map(tag -> {
                    TagVo tagVO = new TagVo();
                    tagVO.setId(tag.getId());
                    tagVO.setName(tag.getName());
                    return tagVO;
                }).collect(Collectors.toList()));
            }

            // 设置该讨论下的所有评论(默认每十条分页)
            if (commentPageNum > 0 && commentPageSize > 0) {
                PageHelper.startPage(commentPageNum, commentPageSize);
            }
            PageHelper.startPage(commentPageNum, commentPageSize);
            List<Comment> commentList = commentMapper.selectList(
                    commentQueryWrapper.eq("parent_id", topicId));
            PageInfo<Comment> commentPageInfo = new PageInfo<>(commentList);
            PageVo<CommentVo> commentPageVo = new PageVo<>();
            commentPageVo.setPageNum(commentPageInfo.getPageNum());
            commentPageVo.setPages(commentPageInfo.getPages());
            commentPageVo.setNavigatepageNums(commentPageInfo.getNavigatepageNums());
            // 一级评论
            List<CommentVo> commentVoList1 = commentList.stream().map(comment1 -> {

                CommentVo commentVo1 = new CommentVo();

                userInfoQueryWrapper.clear();
                UserInfo info1 = userInfoMapper.selectOne(userInfoQueryWrapper.eq("account", comment1.getCreateUser()));

                commentVo1.setCreateUsername(info1.getUsername());
                commentVo1.setHeadPortrait(info1.getHeadPortrait());
                commentVo1.setCommentId(comment1.getId());
                commentVo1.setCommentContent(comment1.getCommentContent());
                commentVo1.setType(comment1.getType());
                commentVo1.setCreateTime(comment1.getCreateTime());
                User user1 = userMapper.selectById(comment1.getCreateUser());
                commentVo1.setCreateUserHomepageId(user1.getHomepageId());
                commentVo1.setParentId(topicId);
                commentVo1.setParentUserName(userInfo.getUsername());

                // 二级评论
                commentQueryWrapper.clear();
                List<Comment> commentList2 = commentMapper.selectList(commentQueryWrapper.eq("parent_id", comment1.getId()));
                List<CommentVo> commentVoList2 = commentList2.stream().map(comment2 -> {

                    CommentVo commentVo2 = new CommentVo();

                    userInfoQueryWrapper.clear();
                    UserInfo info2 = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", comment2.getCreateUser()));

                    commentVo2.setCreateUsername(info2.getUsername());
                    commentVo2.setHeadPortrait(info2.getHeadPortrait());
                    commentVo2.setCommentId(comment2.getId());
                    commentVo2.setCommentContent(comment2.getCommentContent());
                    commentVo2.setType(comment2.getType());
                    commentVo2.setCreateTime(comment2.getCreateTime());

                    User user2 = userMapper.selectById(comment2.getCreateUser());

                    commentVo2.setCreateUserHomepageId(user2.getHomepageId());
                    commentVo2.setParentId(comment1.getId());
                    commentVo2.setParentUserName(info1.getUsername());

                    String replyTaId = comment2.getReplyTaId();
                    if (!StringUtils.isEmpty(replyTaId) && CommonField.LV3_COMMMENT_TYPE.equals(comment2.getType())) {
                        Comment comment3 = commentMapper.selectById(replyTaId);
                        userInfoQueryWrapper.clear();
                        UserInfo info3 = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", comment3.getCreateUser()));
                        commentVo2.setReplyUsername(info3.getUsername());
                        User user3 = userMapper.selectById(comment3.getCreateUser());
                        commentVo2.setReplyHomepageId(user3.getHomepageId());
                    }
                    return commentVo2;
                }).collect(Collectors.toList());
                commentVo1.setCommentVoList(commentVoList2);
                return commentVo1;
            }).collect(Collectors.toList());

            commentPageVo.setData(commentVoList1);
            topicVO.setCommentPageVo(commentPageVo);

            // 阅读数加一
            topicMapper.incrViewCount(topicId);
        }
        return topicVO;
    }

    public TopicVo publish(String topicId, String topicTitle, String topicContent, String tags, String account) {

        TopicVo topicVO = new TopicVo();

        Topic topic = new Topic();
        topic.setId(StringUtils.isEmpty(topicId) ? commonUtil.randomCode() : topicId);
        topic.setTopicTitle(topicTitle);
        topic.setTopicContent(topicContent);
        topic.setTags(tags);
        topic.setCommentCount(0);
        topic.setViewCount(0);
        topic.setUpdateUser(account);
        topic.setUpdateTime(new Date());
        topic.setLastCommentTime(new Date());

        if (StringUtils.isEmpty(topicId)) {
            topic.setCreateUser(account);
            topic.setCreateTime(new Date());
            topicMapper.insert(topic);
        } else {
            topicMapper.updateById(topic);
        }

        User user = userMapper.selectById(account);
        UserInfo userInfo = userInfoMapper.selectById(user.getUserInfoId());

        // 个人综合贡献值+10
        contributeMapper.incrUserContributeAll(account, 10);

        // 日榜贡献值+10
        UserContributeDayRankTask dayRankTask = new UserContributeDayRankTask(account, userInfo.getUsername(), userInfo.getHeadPortrait(), user.getHomepageId());
        threadUtil.execute(dayRankTask);

        // 生成动态放入线程池
        UserDynamicTask userDynamicTask
                = new UserDynamicTask(CommonField.PUBLISH_DYNAMIC_TYPE, topic.getId(), topic.getId(),account, account);
        threadUtil.execute(userDynamicTask);

        // 标签热度加一
        for (String tag : tags.split(",")) {
            redisUtil.setHashIncr(RedisKey.TAG_HOT_HASH, tag, 1L);
        }

        topicVO.setId(topic.getId());
        return topicVO;
    }

    /**
     * 设置排序方式
     */
    private void setSortOrder(String sortOrder, QueryWrapper<Topic> queryWrapper) {
        if (CommonField.SORT_BY_LAST_COMMENT_TIME_DESC.equals(sortOrder)) {
            queryWrapper.orderByDesc("last_comment_time");
        }
    }
}
