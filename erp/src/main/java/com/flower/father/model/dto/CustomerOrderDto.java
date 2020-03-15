package com.flower.father.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author eiven
 */
@Data
@NoArgsConstructor
public class CustomerOrderDto {
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

    private List<CustomerGoodDto> goods;

    public CustomerOrderDto(Long id, String state, String florist) {
        this.id = id;
        this.state = state;
        this.florist = florist;
    }
}
