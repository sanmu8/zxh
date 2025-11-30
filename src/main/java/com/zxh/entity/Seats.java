package com.zxh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.zxh.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 座位
 * </p>
 *
 * @author zxh
 * @since 2025-11-29
 */
@Data
@ApiModel("座位")
public class Seats extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("座位唯一ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("所属自习室ID	")
    private Integer roomId;

    @ApiModelProperty("座位编号")
    private String seatNumber;

    @ApiModelProperty("X坐标")
    private String posX;

    @ApiModelProperty("Y坐标")
    private String posY;

    @ApiModelProperty("状态(0-可用,1-使用中，2-维修中)")
    private Byte status;

    @TableField(exist = false)
    @ApiModelProperty("所属楼栋ID")
    private Integer buildingId;

    @TableField(exist = false)
    @ApiModelProperty("栋楼名称")
    private String buildingName;

    @TableField(exist = false)
    @ApiModelProperty("自习室名称")
    private String roomName;
}
