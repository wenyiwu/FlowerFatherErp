package com.flower.father.model.po;

import lombok.Data;

/**
 * @author eiven
 */
@Data
public class PurchaseGoodPo {
    private Long id;

    private Long orderId;

    private Long materialId;

    private Integer number;

    private Float price;
}
