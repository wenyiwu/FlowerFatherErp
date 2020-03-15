package com.flower.father.model.response;

import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author eiven
 */
@Data
public class CustomerOrderResponse {
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

    /**
     * 花艺师
     */
    private String florist;

    private String remarks;

    private String state;

    private Float freight;

    private List<CustomerGoodResponse> goods;

    public CustomerOrderResponse() {}


    public CustomerOrderResponse(Long id) {
        this.id = id;
        this.externalOrderId = "11111";
        this.orderType = "123";
        this.customerSource = "1";
        this.customerName = "12";
        this.customerTel = "123";
        this.orderCreatedAt = "1234";
        this.deliveryAddress = "12345";
        this.deliveryAt = "123456";
        this.amount = 10.0f;
        this.florist = "1234567";
        this.state = "未完成";
        this.freight = 10.0f;
        this.remarks = "123123";
        CustomerGoodResponse good1 = new CustomerGoodResponse(101L);
        CustomerGoodResponse good2 = new CustomerGoodResponse(102L);
        this.goods = Lists.newArrayList(good1, good2);
    }
}
