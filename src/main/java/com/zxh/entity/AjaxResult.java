package com.zxh.entity;

import lombok.Data;

@Data
public class AjaxResult {
    private int code; // 状态码
    private String message; // 响应消息
    private Object data; // 响应数据

    // 构造函数
    public AjaxResult() {
    }

    public static AjaxResult success() {
        return success(null);
    }

    public static AjaxResult success(Object data) {
        AjaxResult result = new AjaxResult();
        result.setCode(200); // 成功状态码
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static AjaxResult error(String message) {
        AjaxResult result = new AjaxResult();
        result.setCode(500); // 错误状态码
        result.setMessage(message);
        return result;
    }

    public static AjaxResult tokenError() {
        AjaxResult result = new AjaxResult();
        result.setCode(401); // 错误状态码
        result.setMessage("token校验失败！请重新登录！");
        return result;
    }
}
