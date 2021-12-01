package com.yeexang.community.web.controller;

import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.filter.Filter;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.util.IpUtil;
import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.vo.PageVO;
import com.yeexang.community.pojo.vo.TopicVO;
import com.yeexang.community.web.service.SectionSev;
import com.yeexang.community.web.service.TopicSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author yeeq
 * @date 2021/7/25
 */
@Slf4j
@RestController
@RequestMapping("topic")
@Api(tags = "帖子管理 Controller")
public class TopicCon {

    @Autowired
    private TopicSev topicSev;

    @Autowired
    private SectionSev sectionSev;

    @Autowired
    private IpUtil ipUtil;

    @PostMapping("page")
    @ApiOperation(value = "获取帖子分页")
    public ResponseEntity<PageVO> page(@RequestBody RequestEntity<TopicDTO> requestEntity) {

        TopicDTO topicDTO;
        List<TopicDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()) {
            topicDTO = new TopicDTO();
        } else {
            topicDTO = data.get(0);
        }

        Integer pageNum = requestEntity.getPageNum();
        Integer pageSize = requestEntity.getPageSize();
        Filter filter = requestEntity.getFilter();
        PageVO<TopicVO> pageVO = topicSev.getTopicList(pageNum, pageSize, topicDTO, filter);

        return new ResponseEntity<>(pageVO);
    }

    @PostMapping("visit")
    @ApiOperation(value = "访问帖子")
    public ResponseEntity<TopicVO> visit(@RequestBody RequestEntity<TopicDTO> requestEntity,
                                          HttpServletRequest request) {

        TopicDTO topicDTO;
        List<TopicDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty() || data.get(0) == null) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            topicDTO = data.get(0);
        }

        Optional<TopicVO> optional = topicSev.visit(topicDTO.getTopicId(), ipUtil.getIpAddr(request));

        if (optional.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.RESPONSE_DATA_EMPTY);
        }

        return new ResponseEntity<>(optional.get());
    }

    @PostMapping("publish")
    @ApiOperation(value = "发布帖子")
    public ResponseEntity<TopicVO> publish(@RequestBody RequestEntity<TopicDTO> requestEntity, HttpServletRequest request) {

        String account = request.getAttribute("account").toString();

        TopicDTO topicDTO;
        List<TopicDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty() || data.get(0) == null) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            topicDTO = data.get(0);
        }

        // 参数校验
        if (StringUtils.isEmpty(topicDTO.getTopicTitle())) {
            return new ResponseEntity<>(ServerStatusCode.TOPIC_TITLE_EMPTY);
        }
        if (topicDTO.getTopicTitle().length() > 20) {
            return new ResponseEntity<>(ServerStatusCode.TOPIC_TITLE_TOO_LONG);
        }
        if (StringUtils.isEmpty(topicDTO.getTopicContent())) {
            return new ResponseEntity<>(ServerStatusCode.TOPIC_CONTENT_EMPTY);
        }
        if (topicDTO.getTopicContent().length() > 1000) {
            return new ResponseEntity<>(ServerStatusCode.TOPIC_CONTENT_TOO_LONG);
        }
        if (StringUtils.isEmpty(topicDTO.getSection())) {
            return new ResponseEntity<>(ServerStatusCode.SECTION_EMPTY);
        }
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setSectionId(topicDTO.getSection());
        if (sectionSev.getSectionList(sectionDTO).isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.SECTION_NOT_EXIST);
        }

        topicDTO.setCreateUser(account);
        topicDTO.setUpdateUser(account);
        Optional<TopicVO> optional = topicSev.publish(topicDTO);

        if (optional.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.RESPONSE_DATA_EMPTY);
        }

        return new ResponseEntity<>(optional.get());
    }

    /*@PostMapping("like")
    @ApiOperation(value = "点赞帖子")
    public ResponseEntity<TopicDTO> like(@RequestBody RequestEntity<TopicDTO> requestEntity, HttpServletRequest request) {

        String account = request.getAttribute("account").toString();

        TopicDTO topicDTO;
        List<TopicDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            topicDTO = data.get(0);
        }

        // 点赞
        List<Topic> topicList = topicSev.like(topicDTO, account);
        if (topicList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.DATA_NOT_FOUND);
        }

        List<TopicDTO> topicDTOList = topicList.stream()
                .map(topic -> {
                    TopicDTO dto = null;
                    Optional<BaseDTO> optional = topic.toVO();
                    if (optional.isPresent()) {
                        dto = (TopicDTO) optional.get();
                        UserDTO param = new UserDTO();
                        param.setAccount(dto.getCreateUser());
                        List<User> userList = userSev.selectUser(param);
                        if (!userList.isEmpty()) {
                            String username = userList.get(0).getUsername();
                            dto.setCreateUserName(username);
                        }
                    }
                    return dto;
                }).collect(Collectors.toList());

        return new ResponseEntity<>(topicDTOList);
    }*/
}
