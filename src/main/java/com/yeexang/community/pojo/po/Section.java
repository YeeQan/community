package com.yeexang.community.pojo.po;

import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.SectionDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author yeeq
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Section extends BasePO {

    /**
     * 主键
     */
    private String id;

    /**
     * 分区id
     */
    private String sectionId;

    /**
     * 分区名
     */
    private String sectionName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 删除标识
     */
    private Boolean delFlag;

    @Override
    public BaseDTO toDTO() {
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setSectionId(sectionId);
        sectionDTO.setSectionName(sectionName);
        return sectionDTO;
    }
}
