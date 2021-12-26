package com.yeexang.community.pojo.po.ext;

import com.yeexang.community.pojo.po.User;
import com.yeexang.community.pojo.po.UserDynamic;
import com.yeexang.community.pojo.po.UserHomepage;
import com.yeexang.community.pojo.po.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户个人主页 PO EXT
 *
 * @author yeeq
 * @date 2021/12/19
 */
@Data
public class UserHomepageExt {

    private UserHomepage userHomepage;
    private User user;
    private UserInfo userInfo;
    private List<UserDynamic> userDynamicList;
}
