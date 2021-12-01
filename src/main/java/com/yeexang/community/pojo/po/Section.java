package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.SectionVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;

/**
 * 专栏 PO
 *
 * @author yeeq
 * @date 2021/7/19
 */
@Data
@Slf4j
@TableName("y_c_section")
@EqualsAndHashCode(callSuper = false)
public class Section extends BasePO {

    /**
     * 主键
     */
    @TableField("id")
    private String id;

    /**
     * 分区id
     */
    @TableId("section_id")
    private String sectionId;

    /**
     * 分区名
     */
    @TableField("section_name")
    private String sectionName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建者
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新者
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 删除标识
     */
    @TableField("del_flag")
    private Boolean delFlag;

    @Override
    public Optional<BaseVO> toVO() {
        SectionVO sectionVO;
        try {
            sectionVO = new SectionVO();
            sectionVO.setSectionId(sectionId);
            sectionVO.setSectionName(sectionName);
        } catch (Exception e) {
            log.error("Section toVO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(sectionVO);
    }
}
