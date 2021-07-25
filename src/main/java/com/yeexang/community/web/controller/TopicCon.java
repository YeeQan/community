package com.yeexang.community.web.controller;

import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.Topic;
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

    @PostMapping("publish")
    public ResponseEntity<TopicDTO> register(@RequestBody RequestEntity<TopicDTO> requestEntity, HttpServletRequest request) {

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
        if (topicDTO.getTopicTitle().length() > 1000) {
            return new ResponseEntity<>(ServerStatusCode.TOPIC_CONTENT_TOO_LONG);
        }
        if (StringUtils.isEmpty(topicDTO.getSection())) {
            return new ResponseEntity<>(ServerStatusCode.TOPIC_TITLE_EMPTY);
        }
        /*if () {
            return new ResponseEntity<>(ServerStatusCode.TOPIC_TITLE_TOO_LONG);
        }*/

        List<Topic> topicList = topicSev.publish(topicDTO, account);
        if (topicList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.UNKNOWN);
        }

        List<TopicDTO> topicDTOList = topicList.stream()
                .map(topic -> (TopicDTO) topic.toDTO()).collect(Collectors.toList());

        log.info("TopicCon publish end --------------------------------");

        return new ResponseEntity<>(topicDTOList);
    }
}
