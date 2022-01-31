package com.yeexang.community.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.filter.Filter;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.common.redis.RedisUtil;
import com.yeexang.community.common.task.UserLikeTopDynamicTask;
import com.yeexang.community.common.task.UserPubTopDynamicTask;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.common.util.FilterUtil;
import com.yeexang.community.common.util.ThreadUtil;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.dao.UserDynamicDao;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.vo.*;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.TopicLikeSev;
import com.yeexang.community.web.service.TopicSev;
import com.yeexang.community.web.service.UserSev;
import com.yeexang.community.web.service.impl.base.BaseSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/7/25
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TopicSevImpl extends BaseSev<Topic, String> implements TopicSev {

    @Autowired
    private UserSev userSev;

    @Autowired
    private CommentSev commentSev;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private UserDynamicDao userDynamicDao;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FilterUtil filterUtil;

    @Autowired
    private TopicLikeSev topicLikeSev;

    @Autowired
    private ThreadUtil threadUtil;

    @Override
    protected RedisKey getRedisKey() {
        return RedisKey.TOPIC;
    }

    @Override
    protected BaseMapper<Topic> getBaseMapper() {
        return topicDao;
    }

    @Override
    protected Class<Topic> getEntityClass() {
        return Topic.class;
    }

    @Override
    public PageVO<TopicVO> getTopicList(Integer pageNum, Integer pageSize, TopicDTO topicDTO, Filter filter) {
        PageVO<TopicVO> pageVO = new PageVO<>();
        try {
            Optional<BasePO> optional = topicDTO.toPO();
            if (optional.isPresent()) {
                Topic topic = (Topic) optional.get();
                QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
                queryWrapper.setEntity(topic);
                // 设置筛选条件
                filterUtil.setFilterCondition(filter, queryWrapper);
                // 是否开启分页
                if (pageNum > 0 && pageSize > 0) {
                    PageHelper.startPage(pageNum, pageSize);
                }
                List<Topic> topicList = topicDao.selectList(queryWrapper);
                PageInfo<Topic> pageInfo = new PageInfo<>(topicList);

                pageVO.setPageNum(pageInfo.getPageNum());
                pageVO.setPages(pageInfo.getPages());
                pageVO.setNavigatepageNums(pageInfo.getNavigatepageNums());

                List<TopicVO> topicVOList = topicList.stream()
                        .map(po -> {
                            TopicVO vo = null;
                            Optional<BaseVO> baseVOptional = po.toVO();
                            if (baseVOptional.isPresent()) {
                                vo = (TopicVO) baseVOptional.get();
                                // 设置用户头像
                                Optional<UserVO> userVOP = userSev.getUserVOByAccount(po.getCreateUser());
                                if (userVOP.isPresent()) {
                                    UserVO userVO = userVOP.get();
                                    vo.setHeadPortrait(userVO.getHeadPortrait());
                                }
                            }
                            return vo;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                pageVO.setList(topicVOList);
            }
        } catch (Exception e) {
            log.error("TopicSev getTopicList errorMsg: {}", e.getMessage(), e);
            return new PageVO<>();
        }
        return pageVO;
    }

    @Override
    public Optional<TopicVO> visit(String topicId, String ipAddr, String account) {
        TopicVO topicVO = null;
        try {
            Topic topic = selectById(topicId);
            if (topic != null) {
                // 增加浏览次数
                topicVisitCountIncrease(topic, ipAddr);
                Optional<BaseVO> baseVOptional = topic.toVO();
                if (baseVOptional.isPresent()) {
                    topicVO = (TopicVO) baseVOptional.get();
                    Optional<UserVO> userVOP = userSev.getUserVOByAccount(topic.getCreateUser());
                    if (userVOP.isPresent()) {
                        UserVO userVO = userVOP.get();
                        // 设置用户名和头像
                        topicVO.setCreateUserName(userVO.getUsername());
                        topicVO.setHeadPortrait(userVO.getHeadPortrait());
                        // 设置评论
                        CommentDTO commentDTO = new CommentDTO();
                        commentDTO.setParentId(topic.getTopicId());
                        List<CommentVO> firstLevelComment = commentSev.getFirstLevelComment(commentDTO);
                        topicVO.setCommentVOList(firstLevelComment);
                        // 点赞状态 & 是否作者访问
                        if (account != null) {
                            boolean likeStatus = topicLikeSev.getTopicLikeStatus(topicId, account);
                            topicVO.setLikeStatus(likeStatus);
                            if (account.equals(topic.getCreateUser())) {
                                topicVO.setCreaterVisit(true);
                            }
                        } else {
                            topicVO.setLikeStatus(false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("TopicSev visit errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Optional.empty();
        }
        return Optional.ofNullable(topicVO);
    }

    @Override
    public Optional<TopicVO> publish(TopicDTO topicDTO) {
        TopicVO topicVO = null;
        try {
            Optional<BasePO> optional = topicDTO.toPO();
            if (optional.isPresent()) {
                Topic topic = (Topic) optional.get();
                topic.setId(commonUtil.uuid());
                String topicId = commonUtil.randomCode();
                topic.setTopicId(topicId);
                topic.setCommentCount(0);
                topic.setViewCount(0);
                topic.setLikeCount(0);
                topic.setEssentialStatus(false);
                topic.setRecommendedStatus(false);
                topic.setLastCommentTime(new Date());
                topic.setCreateTime(new Date());
                topic.setUpdateTime(new Date());

                save(topic, topic.getTopicId());
                topic = selectById(topicId);

                Optional<BaseVO> topicVOP = topic.toVO();
                if (topicVOP.isPresent()) {
                    topicVO = (TopicVO) topicVOP.get();
                    Optional<UserVO> userVOP = userSev.getUserVOByAccount(topic.getCreateUser());

                    if (userVOP.isPresent()) {
                        UserVO userVO = userVOP.get();
                        topicVO.setCreateUserName(userVO.getUsername());
                        topicVO.setHeadPortrait(userVO.getHeadPortrait());
                    }
                    // 异步保存动态
                    threadUtil.execute(new UserPubTopDynamicTask(topic));
                }
            }
        } catch (Exception e) {
            log.error("TopicSev publish errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Optional.empty();
        }
        return Optional.ofNullable(topicVO);
    }

    /**
     * 更新帖子评论次数
     *
     * @param topicId topicId
     */
    @Override
    public void topicCommentCountIncrease(String topicId) {
        try {
            if (!StringUtils.isEmpty(topicId)) {
                Topic topic = selectById(topicId);
                // 评论次数加一
                topic.setCommentCount(topic.getCommentCount() + 1);
                // 保存最新评论时间
                topic.setLastCommentTime(new Date());
                // 保存
                save(topic, topicId);
            }
        } catch (Exception e) {
            log.error("TopicSev topicCommentCountIncrease errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public void like(TopicDTO topicDTO, String account) {
        try {
            // 增加点赞数
            Topic topic = selectById(topicDTO.getTopicId());
            if (topic != null) {
                boolean likeStatus = topicLikeSev.getTopicLikeStatus(topic.getTopicId(), account);
                if (likeStatus) {
                    topic.setLikeCount(topic.getLikeCount() - 1);
                } else {
                    topic.setLikeCount(topic.getLikeCount() + 1);
                }
                save(topic, topic.getTopicId());
                // 异步保存动态
                if (topicLikeSev.getTopicLikeOne(topic.getTopicId(), account).isEmpty()) {
                    threadUtil.execute(new UserLikeTopDynamicTask(topic));
                }
                // 保存点赞记录
                topicLikeSev.saveTopicLikeStatus(topic.getTopicId(), account);
            }
        } catch (Exception e) {
            log.error("TopicSev like errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * 更新帖子浏览次数
     *
     * @param topic  topic
     * @param ipAddr ipAddr
     */
    private void topicVisitCountIncrease(Topic topic, String ipAddr) {
        if (!StringUtils.isEmpty(ipAddr)) {
            Optional<String> rvOP = redisUtil.getValue(RedisKey.TOPIC_VISIT_COUNT_LIMIT, ipAddr + "_" + topic.getTopicId());
            // 缓存值已经过期，有效访问，浏览次数加一，并重新设置
            if (rvOP.isEmpty()) {
                // 浏览次数加一
                topic.setViewCount(topic.getViewCount() + 1);
                // 保存
                save(topic, topic.getTopicId());
                // 设置 ip 浏览次数限制
                redisUtil.setValue(RedisKey.TOPIC_VISIT_COUNT_LIMIT, ipAddr + "_" + topic.getTopicId(),
                        CommonField.TOPIC_VISIT_COUNT_CONSTANT);
            }
        }
    }
}
