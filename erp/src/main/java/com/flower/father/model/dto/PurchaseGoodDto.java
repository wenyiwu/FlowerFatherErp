package com.flower.father.model.dto;

import lombok.Data;

/**
 * @author eiven
 */
@Data
public class PurchaseGoodDto {
    private Long id;

    private Long orderId;

    private Long goodId;

    private Integer number;

    private Float price;

    private String name;

    private String level;

    private String classify;

    private String colour;
}
