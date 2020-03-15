package com.flower.father.model.param;

import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author eiven
 */ //{"Name":"123","Tel":"12312312312","DeliveryAddress":"321","DeliveryTime":"123","Amount":123,"Deposit":321,"Remarks":"123","Goods":[{"ID":"121","Number":3}]}
@Data
public class CustomerOrderParam {
    private Long id;
    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 外部订单号
     */
    private String externalOrderId;

    /**
     * 客户来源
     */
    private String customerSource;

    private String customerName;

    private String customerTel;

    /**
     * 下单时间
     */
    private String orderCreatedAt;

    /**
     * 预定送达时间
     */
    private String deliveryAddress;

    private String deliveryAt;

    private Float amount;

    private Float freight;

    /**
     * 花艺师
     */
    private String florist;

    private String remarks;

    private List<CustomerGoodParam> goods;

    public CustomerOrderParam() {}


    public CustomerOrderParam(String orderType) {
        this.orderType = orderType;
        this.customerSource = "1";
        this.customerName = "12";
        this.customerTel = "123";
        this.orderCreatedAt = "1234";
        this.deliveryAddress = "12345";
        this.deliveryAt = "123456";
        this.amount = 10.0f;
        this.florist = "1234567";
        CustomerGoodParam good1 = new CustomerGoodParam(101L);
        CustomerGoodParam good2 = new CustomerGoodParam(102L);
        this.goods = Lists.newArrayList(good1, good2);
    }
}
