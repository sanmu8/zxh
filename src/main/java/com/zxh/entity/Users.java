package com.zxh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zxh.common.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zxh
 * @since 2025-11-27
 */
@Data
public class Users extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 微信openid
     */
    private Integer openid;

    /**
     * 密码
     */
    private String password;

    /**
     * 学号
     */
    private Integer studentId;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型(0-学生,1-管理员)
     */
    private String userType;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 状态(0-正常,1-冻结)
     */
    private String status;

    /**
     * 违规次数
     */
    private String violationCount;

    /**
     * 累计处罚金额
     */
    private String totalPenalty;

}
