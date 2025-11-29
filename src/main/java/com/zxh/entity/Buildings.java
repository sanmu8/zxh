package com.zxh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.zxh.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 栋楼
 * </p>
 *
 * @author zxh
 * @since 2025-11-29
 */
@Data
@ApiModel("栋楼")
public class Buildings extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("栋楼名称")
    private String buildingName;

    @ApiModelProperty("详细地址")
    private String buildingAddress;
}
