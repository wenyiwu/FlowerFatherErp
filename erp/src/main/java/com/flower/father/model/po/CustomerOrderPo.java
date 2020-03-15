package com.flower.father.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerOrderPo {
    private Long id;

    /**
     * 订单类型
     */
    private String externalOrderId;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 客户来源
     */
    private String customerSource;

    private String customerName;

    private String customerTel;

    private Float amount;

    /**
     * 花艺师
     */
    private String florist;

    private String remarks;

    private String state;

    private Float freight;

    /**
     * 预定送达时间
     */
    private String deliveryAddress;

    private Date deliveryAt;
    /**
     * 下单时间
     */
    private Date orderCreatedAt;

    private Date createdAt;
}
