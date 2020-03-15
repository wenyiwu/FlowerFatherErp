package com.flower.father.enums;

public enum CustomerOrderStatusEnum {
    NOT_CONFIRM(101, "未完成"),
    CONFIRM(102, "已完成"),
    CANCEL(103, "已取消");

    private int code;
    private String desc;

    private CustomerOrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
