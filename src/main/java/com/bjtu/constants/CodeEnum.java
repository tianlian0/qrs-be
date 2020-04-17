package com.bjtu.constants;

public enum CodeEnum {

    OK(200, "成功"),

    PRIVILEGE_NEED(452, "无权限"),
    PARAM_ILLEGAL(453, "参数非法"),
    LOGIN_NEED(454, "未登录"),
    LOGIN_NEED_PERMISSION(455, "登录权限不足"),

    SERVER_ERROR(-1, "网络错误请重试"),

    FAIL(500, "失败"),
    REDIS_LOCK_TIMEOUT(502, "锁定超时"),
    SYSTEM_BUSY(508, "系统繁忙，请稍后重试");

    private int code;
    private String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
