package com.styeeqan.community.web.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.styeeqan.community.common.util.CommonUtil;
import com.styeeqan.community.mapper.CommentMapper;
import com.styeeqan.community.mapper.TopicMapper;
import com.styeeqan.community.mapper.UserInfoMapper;
import com.styeeqan.community.pojo.po.Comment;
import com.styeeqan.community.pojo.po.Topic;
import com.styeeqan.community.pojo.po.UserInfo;
import com.styeeqan.community.pojo.vo.CommentVO;
import com.styeeqan.community.pojo.vo.PageVO;
import com.styeeqan.community.pojo.vo.TopicVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
                    UserInfo userInfo = userInfoMapper.selectbyAccount(po.getCreateUser());
                    if (userInfo != null) {
                        vo.setCreateUserName(userInfo.getCreateUser());
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
            UserInfo userInfo = userInfoMapper.selectbyAccount(topic.getCreateUser());
            topicVO.setCreateUserName(userInfo.getUsername());
            topicVO.setHeadPortrait(userInfo.getHeadPortrait());
            // 设置评论
            List<Comment> commentList = commentMapper.selectCommentByParentId(topicId);
            List<CommentVO> commentVOList = commentList.stream().map(comment -> {
                CommentVO commentVO = new CommentVO();
                UserInfo info = userInfoMapper.selectbyAccount(comment.getCreateUser());
                commentVO.setCreateUsername(info.getUsername());
                commentVO.setHeadPortrait(info.getHeadPortrait());
                commentVO.setCommentContent(comment.getCommentContent());
                commentVO.setCreateTime(comment.getCreateTime());
                return commentVO;
            }).collect(Collectors.toList());

            topicVO.setCommentVOList(commentVOList);

            // 阅读数加一
            topicMapper.incrViewCount(topicId);
        }
        return topicVO;
    }

    public TopicVO publish(String topicId, String topicTitle, String topicContent, String account) {

        TopicVO topicVO = new TopicVO();

        Topic topic = new Topic();
        topic.setId(StringUtils.isEmpty(topicId) ? commonUtil.randomCode() : topicId);
        topic.setTopicTitle(topicTitle);
        topic.setTopicContent(topicContent);
        topic.setCommentCount(0);
        topic.setViewCount(1);
        topic.setUpdateUser(account);
        topic.setUpdateTime(new Date());

        if (StringUtils.isEmpty(topicId)) {
            topic.setCreateUser(account);
            topic.setCreateTime(new Date());
            topicMapper.insert(topic);
        } else {
            topicMapper.updateById(topic);
        }

        topicVO.setId(topic.getId());
        return topicVO;
    }
}
