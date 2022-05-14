package com.yeexang.community.web.controller;

import com.yeexang.community.common.annotation.RateLimiterAnnotation;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.vo.SectionVO;
import com.yeexang.community.web.service.SectionSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/8/9
 */
@Slf4j
@RestController
@RequestMapping("section")
@Api(tags = "专栏管理 Controller")
public class SectionCon {

    @Autowired
    private SectionSev sectionSev;

    @PostMapping("list")
    @ApiOperation(value = "获取分区列表")
    @RateLimiterAnnotation(permitsPerSecond = 2.0)
    public ResponseEntity<SectionVO> list(@RequestBody(required = false) RequestEntity<SectionDTO> requestEntity) {

        SectionDTO sectionDTO = requestEntity == null ? new SectionDTO() : requestEntity.getData().get(0);

        List<SectionVO> sectionVOList = sectionSev.getSectionList(sectionDTO);

        return new ResponseEntity<>(sectionVOList);
    }
}
