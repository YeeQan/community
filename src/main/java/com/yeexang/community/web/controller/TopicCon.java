package com.yeexang.community.web.controller;

import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.SectionSev;
import com.yeexang.community.web.service.TopicSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/7/25
 */
@Slf4j
@RestController
@RequestMapping("topic")
public class TopicCon {

    @Autowired
    private TopicSev topicSev;

    @Autowired
    private SectionSev sectionSev;

    @Autowired
    private CommentSev commentSev;

    @PostMapping("getPage")
    public ResponseEntity<?> getPage(@RequestBody RequestEntity<TopicDTO> requestEntity, HttpServletRequest request) {

        log.info("TopicCon getPage start --------------------------------");

        Integer pageNum = requestEntity.getPageNum();
        Integer pageSize = requestEntity.getPageSize();

        TopicDTO topicDTO;
        List<TopicDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()) {
            topicDTO = new TopicDTO();
        } else {
            topicDTO = data.get(0);
        }

        PageInfo pageInfo = topicSev.getPage(pageNum, pageSize, topicDTO);
        if (pageInfo == null || pageInfo.getTotal() == 0) {
            return new ResponseEntity<>(ServerStatusCode.DATA_NOT_FOUND);
        }

        List<Topic> topicList = pageInfo.getList();
        List<TopicDTO> topicDTOList = topicList.stream()
                .map(topic -> (TopicDTO) topic.toDTO()).collect(Collectors.toList());

        pageInfo.setList(topicDTOList);

        log.info("TopicCon getPage end --------------------------------");

        return new ResponseEntity<>(pageInfo);
    }

    @PostMapping("visit")
    public ResponseEntity<TopicDTO> visit(@RequestBody RequestEntity<TopicDTO> requestEntity, HttpServletRequest request) {

        log.info("TopicCon visit start --------------------------------");

        TopicDTO topicDTO = requestEntity.getData().get(0);
        List<Topic> topicList = topicSev.getTopic(topicDTO);
        List<TopicDTO> topicDTOList = topicList.stream()
                .map(topic -> (TopicDTO) topic.toDTO()).collect(Collectors.toList());

        topicDTOList.forEach(topicDTO1 -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setParentId(topicDTO1.getTopicId());
            List<Comment> commentList = commentSev.getCommentList(commentDTO);
            List<CommentDTO> commentDTOList = commentList.stream()
                    .map(comment -> (CommentDTO) comment.toDTO()).collect(Collectors.toList());
            topicDTO1.setCommentDTOList(commentDTOList);
        });

        log.info("TopicCon visit end --------------------------------");

        return new ResponseEntity<>(topicDTOList);
    }

    @PostMapping("publish")
    public ResponseEntity<TopicDTO> publish(@RequestBody RequestEntity<TopicDTO> requestEntity, HttpServletRequest request) {

        log.info("TopicCon publish start --------------------------------");

        String account = request.getAttribute("account").toString();
        TopicDTO topicDTO = requestEntity.getData().get(0);

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
        if (sectionSev.getSection(sectionDTO).isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.SECTION_NOT_EXIST);
        }

        List<Topic> topicList = topicSev.publish(topicDTO, account);
        if (topicList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.DATA_NOT_FOUND);
        }

        List<TopicDTO> topicDTOList = topicList.stream()
                .map(topic -> (TopicDTO) topic.toDTO()).collect(Collectors.toList());

        log.info("TopicCon publish end --------------------------------");

        return new ResponseEntity<>(topicDTOList);
    }

    @PostMapping("like")
    public ResponseEntity<TopicDTO> like(@RequestBody RequestEntity<TopicDTO> requestEntity, HttpServletRequest request) {

        log.info("TopicCon like start --------------------------------");

        String account = request.getAttribute("account").toString();
        TopicDTO topicDTO = requestEntity.getData().get(0);

        List<Topic> topicList = topicSev.like(topicDTO, account);
        if (topicList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.UNKNOWN);
        }

        List<TopicDTO> topicDTOList = topicList.stream()
                .map(topic -> (TopicDTO) topic.toDTO()).collect(Collectors.toList());

        log.info("TopicCon like end --------------------------------");

        return new ResponseEntity<>(topicDTOList);
    }
}
