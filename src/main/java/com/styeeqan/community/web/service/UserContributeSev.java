package com.styeeqan.community.web.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.DateUtil;
import com.styeeqan.community.mapper.UserContributeMapper;
import com.styeeqan.community.mapper.UserInfoMapper;
import com.styeeqan.community.mapper.UserMapper;
import com.styeeqan.community.pojo.po.User;
import com.styeeqan.community.pojo.po.UserContribute;
import com.styeeqan.community.pojo.po.UserInfo;
import com.styeeqan.community.pojo.vo.UserContributeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserContributeSev {

    @Autowired
    private UserContributeMapper userContributeMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private RedisUtil redisUtil;

    public List<UserContributeVo> getContributeList(String type) {

        QueryWrapper<UserContribute> userContributeQW = new QueryWrapper<>();
        QueryWrapper<UserInfo> userInfoQW = new QueryWrapper<>();
        QueryWrapper<User> userQW = new QueryWrapper<>();

        List<UserContributeVo> list = new ArrayList<>();

        if (CommonField.CONTRIBUTE_TYPE_ALL.equals(type)) {
            // 获取综合贡献榜前十
            list = userContributeMapper
                    .selectList(userContributeQW.orderByDesc("user_contribute_all").last("limit 10"))
                    .stream()
                    .map(userContribute -> {
                        UserContributeVo vo = new UserContributeVo();
                        vo.setUserContribute(userContribute.getUserContributeAll());
                        UserInfo userInfo = userInfoMapper.selectOne(userInfoQW.eq("account", userContribute.getAccount()));
                        vo.setUsername(userInfo.getUsername());
                        vo.setHeadPortrait(userInfo.getHeadPortrait());
                        userInfoQW.clear();
                        User user = userMapper.selectById(userContribute.getAccount());
                        vo.setHomepageId(user.getHomepageId());
                        userQW.clear();
                        return vo;
                    })
                    .collect(Collectors.toList());
            userContributeQW.clear();
        } else if (CommonField.CONTRIBUTE_TYPE_DAY_RANK.equals(type)) {
            String dateStr = dateUtil.getDateStr(new Date(), DateUtil.parse_date_pattern_1);
            list = redisUtil
                    .reverseRangeZSet(RedisKey.USER_CONTRIBUTE_DAY, dateStr, 0, 9)
                    .stream()
                    .map(json -> {
                        Double score = redisUtil.scoreZSet(RedisKey.USER_CONTRIBUTE_DAY, dateStr, json);
                        UserContributeVo vo = JSON.parseObject(json, UserContributeVo.class);
                        vo.setUserContribute(score.intValue());
                        return vo;
                    })
                    .collect(Collectors.toList());
        } /*else if (CommonField.CONTRIBUTE_TYPE_WEEK_RANK) {
            // 获取日榜前十
            Date monday = dateUtil.getCurWeekMonday();
            Date today = new Date();
            List<Date> dateList = dateUtil.getBetweenDate(monday, today);
            dateList.add(0, monday);
            if (!monday.equals(today)) {
                dateList.add(dateList.size() - 1, today);
            }
        }*/
        return list;
    }
}
