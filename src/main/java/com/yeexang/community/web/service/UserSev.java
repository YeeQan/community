package com.yeexang.community.web.service;

import com.yeexang.community.common.http.response.SevFuncResult;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.pojo.vo.TopicVO;
import com.yeexang.community.pojo.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * 用户管理 Service
 *
 * @author yeeq
 * @date 2021/7/23
 */
public interface UserSev {

    /**
     * 用户注册
     *
     * @param userDTO userDTO
     * @return SevFuncResult
     */
    SevFuncResult register(UserDTO userDTO);

    /**
     * 用户登录
     *
     * @param userDTO userDTO
     * @return SevFuncResult
     */
    SevFuncResult login(UserDTO userDTO);

    /**
     * 获取该用户发布的帖子
     *
     * @param account account
     * @return List<Topic>
     */
    List<TopicVO> getTopicListByAccount(String account);

    /**
     * 上传头像
     *
     * @param file file
     * @param account account
     * @return SevFuncResult
     */
    SevFuncResult uploadHeadPortrait(MultipartFile file, String account);

    /**
     * 根据 account 获取 UserVO
     * @param account account
     * @return Optional<UserVO>
     */
    Optional<UserVO> getUserVOByAccount(String account);
}