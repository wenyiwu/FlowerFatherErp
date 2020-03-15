package com.flower.father.model.po;

import lombok.Data;

import java.util.Date;

/**
 * @author eiven
 */
@Data
public class PurchaseOrderPo {
    private Long id;

    private Float amount;

    private Float freight;

    private String provider;

    private String remarks;

    private String state;

    private Date createdAt;

    private Date updatedAt;

    private Date orderCreatedAt;
}
