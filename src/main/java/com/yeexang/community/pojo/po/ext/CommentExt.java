package com.yeexang.community.pojo.po.ext;

import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.pojo.po.UserHomepage;
import lombok.Data;

/**
 * 用户评论 PO EXT
 *
 * @author yeeq
 * @date 2021/12/19
 */
@Data
public class CommentExt {

    private Comment comment;
    private User user;
    private UserHomepage userHomepage;
}
