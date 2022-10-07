package com.styeeqan.community.web.controller;

import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.pojo.vo.TagVO;
import com.styeeqan.community.web.service.TagSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("tag")
@Api(tags = "标签管理Controller")
public class TagCon {

    @Autowired
    private TagSev tagSev;

    @PostMapping("list")
    @ApiOperation(value = "获取标签")
    public ResponseEntity<TagVO> list() {
        return new ResponseEntity<>(tagSev.getTagList());
    }
}
