package com.zxh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@ApiModel(value = "Buildings对象", description = "栋楼")
public class Buildings implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "building_id", type = IdType.AUTO)
    private Integer buildingId;

    @ApiModelProperty("栋楼名称")
    private String buildingName;

    @ApiModelProperty("详细地址")
    private String buildingAddress;
}
