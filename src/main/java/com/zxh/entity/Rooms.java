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
 * 自习室
 * </p>
 *
 * @author zxh
 * @since 2025-11-29
 */
@Data
@ApiModel(value = "Rooms对象", description = "自习室")
public class Rooms extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自习室唯一ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("所属楼栋ID")
    private Integer buildingId;

    @ApiModelProperty("楼层信息")
    private String floor;

    @ApiModelProperty("自习室名称")
    private String roomName;

    @ApiModelProperty("总座位数")
    private Integer totalSeats;

    @ApiModelProperty("自习室描述")
    private String description;
}
