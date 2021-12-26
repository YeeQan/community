package com.yeexang.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.ext.CommentExt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论管理 Dao
 *
 * @author yeeq
 * @date 2021/7/20
 */
public interface CommentDao extends BaseMapper<Comment> {

    List<CommentExt> selectCommentListByParentIdAndType(@Param("parentId") String parentId, @Param("commentType") String commentType);
}
