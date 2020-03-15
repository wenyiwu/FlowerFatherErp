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
public class PurchaseOrderDto {
    private Long id;

    private String provider;

    private String remarks;

    private Float amount;

    private Float freight;

    private Date orderCreatedAt;

    private Date createdAt;

    private List<PurchaseGoodDto> goods;
}
