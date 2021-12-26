package com.yeexang.community.pojo.po.ext;

import com.yeexang.community.pojo.po.*;
import lombok.Data;

import java.util.List;

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
}
