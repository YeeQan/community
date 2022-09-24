package com.styeeqan.community.web.service;

import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.exception.CustomizeException;
import com.styeeqan.community.common.util.CommonUtil;
import com.styeeqan.community.mapper.CommentMapper;
import com.styeeqan.community.mapper.TopicMapper;
import com.styeeqan.community.mapper.UserInfoMapper;
import com.styeeqan.community.pojo.po.Comment;
import com.styeeqan.community.pojo.po.UserInfo;
import com.styeeqan.community.pojo.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author yeeq
 * @date 2021/8/2
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentSev {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private TopicMapper topicMapper;

    public CommentVO publish(String parentId, String commentContent, String account) {

        Comment comment = new Comment();
        String commentId = commonUtil.randomCode();
        comment.setId(commentId);
        comment.setParentId(parentId);
        comment.setCommentContent(commentContent);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setCreateUser(account);
        comment.setUpdateUser(account);

        commentMapper.insert(comment);

        Comment commentDB = commentMapper.selectById(commentId);
        if (commentDB != null) {
            CommentVO commentVO = new CommentVO();
            UserInfo info = userInfoMapper.selectbyAccount(commentDB.getCreateUser());
            commentVO.setCreateUsername(info.getUsername());
            commentVO.setHeadPortrait(info.getHeadPortrait());
            commentVO.setCommentId(commentDB.getId());
            commentVO.setCommentContent(commentDB.getCommentContent());
            commentVO.setCreateTime(commentDB.getCreateTime());

            // 评论数加一
            topicMapper.incrCommentCount(commentDB.getParentId());

            return commentVO;
        } else {
            throw new CustomizeException(ServerStatusCode.UNKNOWN);
        }
    }
}