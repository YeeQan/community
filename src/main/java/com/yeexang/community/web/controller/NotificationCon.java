package com.yeexang.community.web.controller;

import com.yeexang.community.common.annotation.RateLimiterAnnotation;
import com.yeexang.community.common.annotation.ReqParamVerify;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.constant.NotificationField;
import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.KeyValueResult;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.util.UserUtil;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.vo.NotificationVO;
import com.yeexang.community.pojo.vo.PageVO;
import com.yeexang.community.web.service.NotificationSev;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yeeq
 * @date 2022/4/19
 */
@Slf4j
@RestController
@RequestMapping("notification")
@Api(tags = "通知管理 Controller")
public class NotificationCon {

    @Autowired
    private NotificationSev notificationSev;

    @ReqParamVerify
    @PostMapping("page")
    @ApiOperation(value = "获取评论通知")
    @RateLimiterAnnotation(permitsPerSecond = 2.0)
    public ResponseEntity<PageVO> page(@RequestBody RequestEntity<NotificationDTO> requestEntity,
                                              HttpServletRequest request) {

        NotificationDTO notificationDTO = requestEntity.getData().get(0);

        String account = request.getAttribute(CommonField.ACCOUNT).toString();

        Integer pageNum = requestEntity.getPageNum();
        Integer pageSize = requestEntity.getPageSize();

        String typeLabel = notificationDTO.getTypeLabel();

        if (!NotificationField.LABEL_VALUE_MAP.containsKey(typeLabel)) {
            return new ResponseEntity<>(ServerStatusCode.NOTIFICATION_TYPE_ERROR);
        }

        PageVO<NotificationVO> pageVO
                = notificationSev.getNotificationList(
                        pageNum, pageSize, account, NotificationField.LABEL_VALUE_MAP.get(typeLabel));

        return new ResponseEntity<>(pageVO);
    }

    @PostMapping("type/list")
    @ApiOperation(value = "获取通知类型列表")
    public ResponseEntity<KeyValueResult> typeList() {

        List<KeyValueResult> list = new ArrayList<>();

        for (String label : NotificationField.LABEL_LIST) {
            String labelName = NotificationField.getLabelName(label);
            if (!StringUtils.isEmpty(labelName)) {
                list.add(new KeyValueResult(labelName, label));
            }
        }

        return new ResponseEntity<>(list);
    }

    @ReqParamVerify
    @PostMapping("clean")
    @ApiOperation(value = "获取通知类型列表")
    public ResponseEntity<?> clean(@RequestBody RequestEntity<NotificationDTO> requestEntity,
                                   HttpServletRequest request) {

        NotificationDTO notificationDTO = requestEntity.getData().get(0);

        String account = request.getAttribute(CommonField.ACCOUNT).toString();

        notificationSev.clean(notificationDTO.getTypeLabel(), account);

        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }
}
