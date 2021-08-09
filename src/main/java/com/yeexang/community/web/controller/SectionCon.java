package com.yeexang.community.web.controller;

import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Section;
import com.yeexang.community.pojo.po.Topic;
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
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/8/9
 */
@Slf4j
@RestController
@RequestMapping("section")
@Api(tags = "分区服务接口")
public class SectionCon {

    @Autowired
    private SectionSev sectionSev;

    @PostMapping("list")
    @ApiOperation(value = "获取分区列表")
    public ResponseEntity<SectionDTO> list(@RequestBody RequestEntity<SectionDTO> requestEntity) {

        SectionDTO sectionDTO;
        List<SectionDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()) {
            sectionDTO = new SectionDTO();
        } else {
            sectionDTO = data.get(0);
        }

        List<Section> sectionList = sectionSev.getSection(sectionDTO);

        if (sectionList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.DATA_NOT_FOUND);
        }

        List<SectionDTO> sectionDTOList = sectionList.stream()
                .map(section -> (SectionDTO) section.toDTO()).collect(Collectors.toList());

        return new ResponseEntity<>(sectionDTOList);
    }
}
